# HourBlue

HourBlue is a Pinterest-adjacent visual discovery website for nature aesthetics, DIY ideas, everyday moments, moods, and curated inspiration.

The MVP goal is to ship a polished, mobile-first discovery experience where visitors can browse visual ideas, open a detail page, and keep exploring through related content.

## Current Status

HourBlue is in MVP foundation and early development.

The project already includes:

- Spring Boot backend
- Next.js frontend
- MySQL database via Docker Compose
- Flyway database migrations
- JWT-based admin authentication foundation
- Cloudinary signed-upload support
- Product and architecture planning docs

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | Next.js App Router, TypeScript, Tailwind CSS |
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Database | MySQL 8 |
| Migrations | Flyway |
| Image storage/CDN | Cloudinary |
| Local dev | Docker Compose |
| Planned hosting | Vercel frontend, Railway backend/database |

## Repository Structure

```txt
HourBlue/
|-- backend/             # Spring Boot API
|-- frontend/            # Next.js app
|-- docs/
|   |-- PLANNING.md      # Product and architecture plan
|   `-- TRACKER.md       # Running project tracker
|-- docker-compose.yml   # Local MySQL + backend setup
`-- README.md
```

## Local Development

From the repo root:

```bash
docker compose up --build
```

In a second terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:3000`.

The backend runs on `http://localhost:8080`.

## Documentation

- Product and architecture plan: `docs/PLANNING.md`
- Running project tracker: `docs/TRACKER.md`
- Backend setup notes: `backend/README.md`
- Frontend setup notes: `frontend/README.md`

## MVP Direction

V1 focuses on:

- Public visual feed
- Categories and moods
- Idea/post detail pages
- Related content discovery
- Search
- Single-admin content management
- Cloudinary image uploads
- Clean production-style architecture
- Backend, API, and frontend testing practice

V1 does not include public accounts, comments, saved boards, payments, or a complex recommendation engine.
