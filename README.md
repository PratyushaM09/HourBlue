# HourBlue

A personal visual journal — a curated, exploratory alternative to a Pinterest board, where a single photo collection can be browsed through multiple lenses (collection, mood, and eventually place/time/weather) instead of one flat feed.

> *"What happens after someone falls in love with one photo?"*
> Pinterest gives them one beautiful moment. HourBlue invites them to discover a hundred more.

## Status

In planning / early development. See docs/PLANNING.md for the full product and architecture documentation.

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | Next.js (App Router), TypeScript, Tailwind CSS, Framer Motion |
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Database | MySQL 8 |
| Image storage/CDN | Cloudinary |
| Frontend hosting | Vercel |
| Backend hosting | Railway |

## Repository Structure

```
hourblue/
├── frontend/          # Next.js app
├── backend/           # Spring Boot app
├── docs/
│   └── PLANNING.md    # Full product & architecture documentation
├── docker-compose.yml # Local dev: backend + MySQL
└── README.md
```

## Local Development

Quick start (from the repo root):

```
cp backend/.env.example backend/.env      # fill in values, see backend/README.md
cp frontend/.env.local.example frontend/.env.local

docker compose up --build                  # MySQL + backend
cd frontend && npm install && npm run dev  # frontend, in a second terminal
```

See `backend/README.md` and `frontend/README.md` for details on each side, and `docs/PLANNING.md` Section 6.4 for the environments overview.

## Documentation

The complete product and engineering documentation - requirements, user journeys, feature specs, data model, API design, architecture, UI design, security, error handling, testing strategy, deployment, coding standards, and the build roadmap - lives in docs/PLANNING.md. It's treated as a living document, updated as decisions evolve during implementation.

## License

Personal project - all rights reserved unless stated otherwise.
