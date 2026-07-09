# Armário — Backend

API RESTful em Spring Boot 3 (Java 17) com Clean Architecture / Hexagonal (Portas e Adaptadores), autenticação JWT e MapStruct.

## Stack

- Java 17, Spring Boot 3, Spring Web / Data JPA / Security
- JWT (jjwt), MapStruct
- **PostgreSQL (profile `dev`, default — a app sempre conecta no Postgres)** · H2 (profile `local`, opcional) · H2 nos testes

## Como rodar

Sem Maven instalado? Use o wrapper (`./mvnw`). A aplicação **sempre conecta no PostgreSQL** — é preciso um Postgres disponível e a `DB_PASSWORD` definida.

### 1. Suba um Postgres (a forma mais fácil: Docker)

```bash
export DB_PASSWORD='sua-senha'
docker compose up -d      # cria banco `armario` e usuario `armario_app`
```

Já tem um Postgres próprio (VM, container Rancher, etc.)? Pule este passo e crie o banco/usuário:
```sql
CREATE DATABASE armario;
CREATE USER armario_app WITH PASSWORD 'sua-senha';
GRANT ALL PRIVILEGES ON DATABASE armario TO armario_app;
```

### 2. Rode a API

```bash
DB_PASSWORD='sua-senha' ./mvnw spring-boot:run         # PostgreSQL (default)

# Postgres em outro host (ex.: VM):
DB_HOST=192.168.x.x DB_PASSWORD='sua-senha' ./mvnw spring-boot:run

# Opcional: H2 em memoria, sem banco externo (nao persiste):
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

A API sobe em `http://localhost:8080`. Sem `DB_PASSWORD` a app falha explicitamente ao subir (proteção contra credencial fraca).

## Variáveis de ambiente

| Variável | Profile | Descrição |
|---|---|---|
| `JWT_SECRET` | todos | Segredo do JWT (>=32 chars). Em produção defina explicitamente; sem ela usa fallback de dev. |
| `JWT_EXPIRATION_MS` | todos | Expiração do token em ms (default `3600000`). |
| `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USER` / `DB_PASSWORD` | `dev` (default) | Conexão PostgreSQL. Defaults: `localhost:5432/armario`, user `armario_app`. `DB_PASSWORD` é obrigatória (sem fallback). |
| `JDBC_DATABASE_URL` / `JDBC_DATABASE_USERNAME` / `JDBC_DATABASE_PASSWORD` | `dev` | Injetadas pelo Heroku; têm precedência sobre as `DB_*`. |

## Endpoints

| Método | Rota | Acesso |
|---|---|---|
| POST | `/auth/cadastro` | público |
| POST | `/auth/login` | público (retorna token JWT) |
| POST/GET/PUT/DELETE | `/api/produtos` | protegido (Bearer token) |

## Estrutura (Hexagonal)

```
domain/    entidades + portas (inbound/outbound)
app/       use cases (services)
adapter/   inbound (controllers, DTOs) e outbound (JPA)
config/    Spring, Security/JWT
```

## Frontend

O frontend fica em repo separado: https://github.com/fabiomm2014/armario-front-end
