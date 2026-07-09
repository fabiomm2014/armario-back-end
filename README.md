# Armário — Backend

API RESTful em Spring Boot 3 (Java 17) com Clean Architecture / Hexagonal (Portas e Adaptadores), autenticação JWT e MapStruct.

## Stack

- Java 17, Spring Boot 3, Spring Web / Data JPA / Security
- JWT (jjwt), MapStruct
- H2 (profile `local`, default) · PostgreSQL (profile `dev`)

## Como rodar

Sem Maven instalado? Use o wrapper (`./mvnw`).

```bash
./mvnw spring-boot:run                                          # H2 em memoria (local, default)
DB_PASSWORD='sua-senha' ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev   # PostgreSQL
```

A API sobe em `http://localhost:8080`.

## Variáveis de ambiente

| Variável | Profile | Descrição |
|---|---|---|
| `JWT_SECRET` | todos | Segredo do JWT (>=32 chars). Em produção defina explicitamente; sem ela usa fallback de dev. |
| `JWT_EXPIRATION_MS` | todos | Expiração do token em ms (default `3600000`). |
| `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USER` / `DB_PASSWORD` | `dev` | Conexão PostgreSQL. `DB_PASSWORD` é obrigatória (sem fallback). |
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
