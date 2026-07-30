# HourBlue Backend

Spring Boot 3 / Java 17 API. See `docs/PLANNING.md` at the repo root (Sections 4–6, 9–13) for the full design.

## Local setup

1. Copy the env template and fill in values:
   ```
   cp .env.example .env
   ```
   At minimum set `ADMIN_SEED_USERNAME` / `ADMIN_SEED_PASSWORD` for your first run (creates the one admin account), and your Cloudinary credentials.

2. From the repo root, start MySQL + backend together:
   ```
   docker compose up --build
   ```
   Or, to run just MySQL in Docker and the backend from IntelliJ (recommended while developing):
   ```
   docker compose up mysql
   ```
   then run `HourblueApplication` directly from IntelliJ with the `.env` values as environment variables (IntelliJ's EnvFile plugin can load `.env` automatically), pointing `DB_URL` at `jdbc:mysql://localhost:3306/hourblue`.

3. Flyway runs automatically on startup and creates the schema (`src/main/resources/db/migration/V1__init_schema.sql`).

4. Verify it's up:
   ```
   curl http://localhost:8080/api/health
   ```

## Adding a migration

New schema changes go in a new versioned file, e.g. `V2__add_something.sql`, never edited into `V1`. Flyway tracks which migrations have run per-database.

## Package layout

- `controller/` — REST endpoints, thin, delegate to services
- `service/` — business logic (related-photos query, slug generation, etc. land here)
- `repository/` — Spring Data JPA repositories
- `model/` — JPA entities
- `dto/` — request/response records
- `config/` — Spring Security, CORS, Cloudinary, admin seeding
- `security/` — JWT issuing/validation
- `exception/` — centralized error handling
