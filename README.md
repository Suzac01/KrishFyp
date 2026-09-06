# GovWeb

GovWeb is a digital government services portal for citizen document requests and municipal application processing.

## Run the client
```bash
npm install
npm run dev
```

## Run the complete stack with Docker
Install Docker Desktop, then run from the project root:

```bash
docker compose up --build
```

The stack contains PostgreSQL 16, the Java 21 Spring Boot API, and the React/Vite frontend served by Nginx. PostgreSQL data is stored in the `govweb-postgres-data` named volume and survives container restarts.

URLs when running with Docker:
- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`

To stop the containers without deleting data:

```bash
docker compose down
```

To remove the database volume as well, use `docker compose down -v`. This permanently deletes the PostgreSQL data.

## Database configuration
Copy `.env.example` to `.env` and change the values before transferring or deploying the project. The default Docker database is named `govweb`, with the development credentials `postgres` / `123`, matching the connection pattern supplied from the previous project.

If the previous PostgreSQL database is named `shringarvastra`, set `POSTGRES_DB=shringarvastra` in `.env` before first startup. GovWeb will then use that database and Hibernate will create/update its tables there. Do not point GovWeb at a database containing unrelated production tables without taking a backup first.

To move existing PostgreSQL data to another computer, export and import a dump rather than copying the Docker volume manually:

```bash
pg_dump -h localhost -p 5432 -U postgres -Fc govweb > govweb.dump
createdb -h localhost -p 5432 -U postgres govweb
pg_restore -h localhost -p 5432 -U postgres -d govweb govweb.dump
```

The client includes the citizen overview, service catalog, application tracking and intake flow, alongside protected admin operations pages. The API exposes JWT authentication at `/api/auth/register` and `/api/auth/login`, dynamic service CRUD at `/api/services`, application CRUD/status operations at `/api/applications`, and user administration at `/api/users`.

## URLs
- Citizen portal: `http://localhost:5173/`
- Login/register: `http://localhost:5173/login`
- Admin dashboard: `http://localhost:5173/admin`
- Admin application queue: `http://localhost:5173/admin/applications`
- Admin service catalog: `http://localhost:5173/admin/services`

Register a citizen account at `/login`. Officer and super-admin accounts must be provisioned by an authorized administrator or directly in the database with a BCrypt password; the backend enforces the role for protected admin APIs.

## Development admin account
When the API starts for the first time, it creates this officer account if the email does not already exist:

- Email: `admin@govweb.local`
- Password: `Admin@12345`
- Admin URL: `http://localhost:5173/admin`

Change these values with `ADMIN_EMAIL` and `ADMIN_PASSWORD`, or disable automatic seeding with `SEED_ADMIN_ENABLED=false`. Do not use the development password in production.
