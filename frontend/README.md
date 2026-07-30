# HourBlue Frontend

Next.js (App Router) + TypeScript + Tailwind CSS. See `docs/PLANNING.md` at the repo root (Section 7) for the full UI design.

## Local setup

1. Install dependencies:
   ```
   npm install
   ```

2. Copy the env template:
   ```
   cp .env.local.example .env.local
   ```
   `NEXT_PUBLIC_API_URL` should point at your running backend (defaults to `http://localhost:8080`).

3. Run the dev server:
   ```
   npm run dev
   ```
   Visit `http://localhost:3000`. The placeholder Home page calls the backend's `/api/health` endpoint, so start the backend first (see `../backend/README.md`) to see `backend status: ok`.

## Structure

- `app/` — App Router pages (file-based routing)
- `components/` — shared UI components (introduce subfolders once a category exceeds ~8-10 components, per Section 13.3)
- `lib/` — API client and utilities

## Notes for the frontend-as-growth-area

Framer Motion is already installed for the subtle page/image transitions described in Section 7.1 — no need to reach for a heavier animation library.
