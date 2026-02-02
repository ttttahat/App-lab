# Lab10 
## Setup

- Java 21, Gradle, Spring Boot 3.2.2
- Dependencies: Spring Web, Spring Security, Spring Data JPA, Spring Validation, SQLite
- Database: SQLite (database.db) with Hibernate Community Dialects
- Security: BCrypt password hashing, OWASP Dependency Check, and JaCoCo coverage

## structure
- controller/: Manages HTTP endpoints and UI routing
- service/: Contains core business logic and security enforcement (User isolation/Hashing)
- repository/: Includes both JPA and JDBC repositories for safe data persistence
- model/ / entity/: Defines the User and Note entities with relational mapping
- security/: Centralized configuration for the SecurityFilterChain and security headers

## api structure


## Method,Path,Description,Access
- POST,/register, Register a new user with BCrypt hashing, Public
- POST,/login, Authenticate user and establish session, Public
- GET,/user/home, User dashboard (protected), User
- POST,/logout, Invalidate session and clear cookies, User
- GET,/notes, Fetch only notes belonging to the logged-in user, User
- POST,/notes/create, Create a new note linked to current user ID, User