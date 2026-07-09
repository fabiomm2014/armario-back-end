# Armário

Monorepo com backend e frontend separados em pastas. Um único `git clone` traz tudo.

```
armario/
├── backend/    API Spring Boot 3 (Java 17, Clean Architecture / Hexagonal + JWT)
└── frontend/   SPA React 18 + TypeScript + Vite
```

## Clonar

```bash
git clone https://github.com/fabiomm2014/armario.git
cd armario
```

## Backend (porta 8080)

```bash
cd backend
mvn spring-boot:run                                          # H2 em memoria (profile local, default)
DB_PASSWORD='sua-senha' mvn spring-boot:run -Dspring-boot.run.profiles=dev   # PostgreSQL
```

## Frontend (porta 5173)

```bash
cd frontend
npm install
cp .env.example .env    # ajuste VITE_API_URL se necessario
npm run dev
```

Acesso via celular na mesma rede: veja `frontend/README-CELULAR.md`.
