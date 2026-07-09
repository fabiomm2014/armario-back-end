# Armário — Backend

API RESTful em Spring Boot 3 (Java 17) com Clean Architecture / Hexagonal (Portas e Adaptadores), autenticação JWT e MapStruct.

## Stack

- Java 17, Spring Boot 3, Spring Web / Data JPA / Security
- JWT (jjwt), MapStruct
- H2 (profile `local`, default) · PostgreSQL (profile `dev`)

## Como rodar

```bash
mvn spring-boot:run                                          # H2 em memoria (local, default)
DB_PASSWORD='sua-senha' mvn spring-boot:run -Dspring-boot.run.profiles=dev   # PostgreSQL
```

A API sobe em `http://localhost:8080`.

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
