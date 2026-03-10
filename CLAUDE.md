# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build

# Run the application
./gradlew bootRun

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.example.vehicles.VehicleDaoTest"

# Run as executable JAR
java -jar build/libs/vehicles-0.0.1-SNAPSHOT.jar
```

## Architecture

This is a Spring Boot 2.7 MVC CRUD application for managing vehicles, using Java 8 and Gradle.

**Layer structure:**

- `domain/Vehicle.java` — JPA entity mapped to the `VEHICLE` table with fields: `id`, `brand`, `model`, `license`, `color`, `releaseYear`
- `repository/VehicleDao.java` — Spring Data JPA repository extending `JpaRepository<Vehicle, Integer>`; uses derived query method `findByBrandOrModelOrLicense` for multi-field search
- `service/VehicleService.java` + `VehicleServiceImpl.java` — service interface/implementation delegating to `VehicleDao`
- `controller/VehicleController.java` — Spring MVC `@Controller` handling HTTP routes and rendering Thymeleaf templates
- `controller/CustomErrorController.java` — custom error page handler

**Routes:**

| Method | Path | Action |
|--------|------|--------|
| GET | `/` | List all vehicles |
| POST | `/find` | Search by brand, model, or license |
| GET | `/create` | Show create form |
| POST | `/save` | Persist new vehicle |
| GET | `/edit/{id}` | Show edit form |
| POST | `/update` | Update existing vehicle |
| GET | `/confirm/{id}` | Show delete confirmation |
| POST | `/delete` | Delete vehicle by id |

**Templates** (Thymeleaf, in `src/main/resources/templates/`): `index.html`, `create.html`, `edit.html`, `confirm.html`, `customError.html`

## Database

- **Production**: PostgreSQL at `localhost:5432/vehicles` (configured in `application.properties`). `spring.jpa.hibernate.ddl-auto=update` auto-creates/updates the schema.
- **Tests**: H2 in-memory database via `@DataJpaTest` (no PostgreSQL required for tests).

The `application.properties` contains the DB username (`jmangarret`) with an empty password — update credentials as needed for your environment.
