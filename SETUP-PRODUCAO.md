# Deploy em produção — Armário (do zero à VM)

Guia personalizado para o setup do Fábio:
- **Windows** + **Git Bash** (sintaxe Unix — nada de `\` nem `.cmd`)
- **VM Linux** com PostgreSQL, servindo backend (systemd) + frontend (Nginx)
- Fluxo **git-flow**: desenvolve na `develop`, valida, abre PR pra `master`, e **faz o deploy a partir da `master`** (branch estável)

> Substitua `IP_DA_VM` pelo IP real da sua VM (ex.: `192.168.x.x`) em todos os passos.

---

## 0. Fluxo git-flow (contexto)

```
develop  ──commit/teste──▶  PR  ──▶  master  ──▶  build + deploy
```

No dia a dia você trabalha na `develop`. Só o que está na `master` vai pra produção.
Para **desenvolver/testar** localmente, clone a `develop`. Para **fazer o deploy de verdade**, clone/atualize a `master`.

---

## 1. Clonar (Windows, Git Bash)

```bash
mkdir -p /c/dev/git && cd /c/dev/git

# desenvolvimento (develop)
git clone -b develop https://github.com/fabiomm2014/armario-back-end.git
git clone -b develop https://github.com/fabiomm2014/armario-front-end.git

# --- ou, para deploy de produção, a branch estável: ---
# git clone -b master https://github.com/fabiomm2014/armario-back-end.git
# git clone -b master https://github.com/fabiomm2014/armario-front-end.git
```

## 2. Gerar o `.jar` do backend

```bash
cd /c/dev/git/armario-back-end
chmod +x mvnw          # só se der "Permission denied"
./mvnw clean package -DskipTests
```
Artefato em `target/armario-back-end-0.0.1-SNAPSHOT.jar` (nome pode variar com a versão).

> Requer **JDK 17** no Windows para o `package`. Se não tiver, instale (Temurin 17) ou gere o `.jar` dentro da própria VM.

## 3. Gerar o build do frontend

Vite 7 exige **Node 20.19+ ou 22.12+** (você está no Node 24 — ok).

Antes de buildar, aponte a API para **mesma origem** (o Nginx vai servir front e API no mesmo host/porta 80):

```bash
cd /c/dev/git/armario-front-end
echo "VITE_API_URL=" > .env.production   # vazio = usa caminhos relativos (/api, /auth) via Nginx
npm install
npm run build
```
Gera a pasta `dist/`.

> Por que `VITE_API_URL=` vazio: o `client.ts` usa `import.meta.env.VITE_API_URL`. Com valor vazio, o Axios usa caminhos relativos e as chamadas caem no mesmo domínio que serve o site — que o Nginx redireciona pro backend. Assim não precisa embutir IP no build.

---

## 4. Preparar a VM (uma vez, via SSH)

```bash
sudo apt update
sudo apt install -y openjdk-17-jre-headless nginx
sudo mkdir -p /opt/armario /var/www/armario /etc/armario
```

### 4.1. Banco de dados (PostgreSQL da VM)

```bash
sudo -u postgres psql
```
```sql
CREATE DATABASE armario;
CREATE USER armario_app WITH PASSWORD 'ESCOLHA_UMA_SENHA_FORTE';
GRANT ALL PRIVILEGES ON DATABASE armario TO armario_app;
\q
```
Guarde essa senha — vai no arquivo de ambiente do passo 6.

---

## 5. Transferir arquivos com o FileZilla

Conexão: `Host: sftp://IP_DA_VM` · `Porta: 22` · usuário/senha do SSH.

- `target/armario-back-end-*.jar`  →  `/opt/armario/armario.jar`
- **conteúdo** de `dist/` (os arquivos de dentro, não a pasta)  →  `/var/www/armario/`

---

## 6. Segredos em arquivo separado (não no .service)

Em vez de escrever senha/segredo direto no `systemd` (fica legível pra qualquer um que ler o arquivo), use um EnvironmentFile com permissão restrita.

Gere um `JWT_SECRET` forte e crie o arquivo:

```bash
sudo tee /etc/armario/armario.env > /dev/null <<EOF
DB_HOST=localhost
DB_PORT=5432
DB_NAME=armario
DB_USER=armario_app
DB_PASSWORD=A_MESMA_SENHA_FORTE_DO_PASSO_4.1
JWT_SECRET=$(openssl rand -base64 48)
EOF

sudo chmod 600 /etc/armario/armario.env
sudo chown root:root /etc/armario/armario.env
```

> Nada de `DB_PASSWORD=postgres` nem `JWT_SECRET` fixo — a senha do banco é a que você criou, e o segredo do JWT é aleatório (48 bytes). `chmod 600` deixa o arquivo legível só pelo root.

---

## 7. Backend como serviço systemd

```bash
sudo nano /etc/systemd/system/armario.service
```
```ini
[Unit]
Description=Armario Backend
After=network.target postgresql.service

[Service]
EnvironmentFile=/etc/armario/armario.env
ExecStart=/usr/bin/java -jar /opt/armario/armario.jar --spring.profiles.active=dev
Restart=on-failure
User=SEU_USUARIO_DA_VM

[Install]
WantedBy=multi-user.target
```
```bash
sudo systemctl daemon-reload
sudo systemctl enable --now armario
sudo systemctl status armario     # deve aparecer active (running)
```

## 8. Nginx: servir o frontend + proxy da API

```bash
sudo nano /etc/nginx/sites-available/armario
```
```nginx
server {
    listen 80;
    server_name _;

    root /var/www/armario;
    index index.html;

    location / {
        try_files $uri /index.html;   # SPA: rotas do React Router caem no index
    }
    location /api/ {
        proxy_pass http://localhost:8080/api/;
    }
    location /auth/ {
        proxy_pass http://localhost:8080/auth/;
    }
}
```
```bash
sudo ln -s /etc/nginx/sites-available/armario /etc/nginx/sites-enabled/
sudo rm -f /etc/nginx/sites-enabled/default
sudo nginx -t
sudo systemctl restart nginx
```

## 9. Testar

Navegador (Windows ou celular na mesma rede): `http://IP_DA_VM` — sem porta.
Cadastro → login → CRUD de produtos deve funcionar.

---

## Atualizar depois de mudar o código (ciclo contínuo)

1. Na `develop`: commit + push, testa local (`./mvnw clean test`, `npm run build`).
2. Abre PR `develop → master`:
   - Backend: https://github.com/fabiomm2014/armario-back-end/compare/master...develop
   - Frontend: https://github.com/fabiomm2014/armario-front-end/compare/master...develop
3. Merge aprovado → na sua máquina, `git checkout master && git pull` nos dois repos.
4. Rebuilda (`./mvnw clean package -DskipTests` e `npm run build`).
5. Sobe pela FileZilla o novo `.jar` e o novo `dist/`, e reinicia:
   ```bash
   sudo systemctl restart armario
   sudo systemctl restart nginx   # só se mudou o dist/ e quiser garantir cache limpo
   ```

---

## Checklist se algo falhar

| Sintoma | Como investigar |
|---|---|
| Serviço não sobe | `sudo journalctl -u armario -n 50` |
| App falha ao conectar no banco | senha/DB errados no `/etc/armario/armario.env`; `sudo systemctl status postgresql` |
| Nginx não reinicia | `sudo nginx -t` (erro de sintaxe) |
| Página abre mas login/produtos dão erro | confira se `/api/` e `/auth/` do Nginx batem com as rotas do backend |
| CORS bloqueando | em produção same-origin (Nginx) não há CORS; se acessar o back direto por outra origem, ajuste `cors.allowed-origin-patterns` |
