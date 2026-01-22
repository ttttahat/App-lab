# Lab10 
## Setup

- Java 21, Maven, Spring Boot 4.0.0
- dependencies Spring Web, Spring Security, Spring Data JPA, Flyway, SQLite
- environment variables stored in `.env`
- database SQLite (`database.db`)
- Flyway migrations located in `src/main/resources/db/migration`

## structure
- `controller/` HTTP endpoints
- `service/`  business logic
- `repository/`  JPA repositories 
- `model/`  User entity  

## api structure


- Method: POST  Path: `/register`       Headers: Content-Type: application/json     Body: email, username, password  Response 201/400
- Method: POST  Path: `/login`          Headers: Content-Type: application/json     Body: Username, password         Response 200/401
- Method: GET   Path: `/user/{userid}`  Headers: Content-Type: applicatin/json      Body: .                          Response 200/404