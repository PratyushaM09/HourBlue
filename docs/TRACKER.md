# HourBlue Project Tracker

This tracker records what we decide and build as HourBlue moves from MVP planning to implementation.

## Current Phase

Phase: MVP foundation

Goal: Build a polished, production-style MVP for a Pinterest-adjacent visual discovery website.

## Working Principles

- Keep the architecture simple, clean, and layered.
- Discuss decisions before coding.
- Build in small milestones.
- Add tests alongside backend behavior.
- Practice API testing with Postman or `.http` files.
- Keep frontend learning explicit: explain components, routing, data fetching, and styling as we build.

## Product Decisions

- Product name: HourBlue
- Product type: curated visual discovery website
- Primary audience: Pinterest visitors interested in nature aesthetics, DIYs, random moments, moods, and visual inspiration
- MVP approach: ship the smallest polished version that proves the discovery loop
- Core loop: feed -> post detail -> related posts -> more browsing
- Public UI language: Ideas
- Code/domain direction: Post
- Active backend domain has been renamed from `Photo` to `Post`
- Public users do not log in for V1
- Single admin manages all content
- V1 should be monetization-ready through optional external, Pinterest, and affiliate links
- V1 does not include payments, public accounts, comments, saved boards, or a recommendation engine
- Batch upload is deferred to V1.5 unless V1 finishes early

## Architecture Decisions

- Frontend: Next.js App Router, TypeScript, Tailwind CSS
- Backend: Spring Boot, Spring Security, Spring Data JPA
- Database: MySQL 8
- Migrations: Flyway
- Image storage/CDN: Cloudinary
- Local dev: Docker Compose for MySQL and backend, Next dev server for frontend
- Deployment target: Vercel for frontend, Railway or similar for backend/database

## Open Product Questions

- What should the final launch category and mood list be?
- Should the first public frontend milestone use mock data before backend APIs, or should backend APIs come first?

## Current Repo Observations

- The repo already contains a clean monorepo structure with `backend/`, `frontend/`, and `docs/`.
- Backend has initial entities, repositories, JWT auth, Cloudinary signature support, and health endpoint.
- Frontend is still a placeholder that calls backend health.
- Existing planning doc is strong but still describes HourBlue more as a personal visual journal than a Pinterest-adjacent product.
- Root `README.md` has been restored to a short project overview; the long plan lives in `docs/PLANNING.md`.
- Some symbols display garbled in PowerShell output; verify whether this is terminal encoding or actual file encoding before editing docs heavily.

## Milestone Plan

### Milestone 0: Foundation Cleanup

Status: In progress

Tasks:

- Restore root `README.md` to a short project overview. Done.
- Update `docs/PLANNING.md` with MVP/product direction changes. Done.
- Decide public naming: idea/post/moment. Done: public UI uses Ideas.
- Confirm schema changes before writing migrations.
- Add tracker and keep it updated. Done.

### Milestone 1: Data Model and Backend Basics

Status: In progress

Tasks:

- Add title/content type fields. Done.
- Add tags support. Done: simple nullable string for V1.
- Add Pinterest/source/affiliate URL fields. Done.
- Rename active backend domain from `Photo` to `Post`. Done.
- Create DTOs for public post responses. Done.
- Create service layer for post read behavior. Done.
- Add public APIs for listing and reading posts. Done.
- Add tests for service and controller behavior. Done.
- Create a Postman collection or `.http` request file. Done: `backend/requests/hourblue-api.http`.

### Milestone 2: Public Frontend With Mock Data

Status: Not started

Tasks:

- Build layout shell and navigation.
- Build reusable visual card.
- Build masonry grid.
- Build home feed.
- Build category and mood listing pages.
- Build post detail page.
- Build related posts UI.

### Milestone 3: Connect Frontend to Backend

Status: Not started

Tasks:

- Replace mock content with API calls.
- Add loading states.
- Add error states.
- Add empty states.
- Add SEO metadata.

### Milestone 4: Admin Content Management

Status: Not started

Tasks:

- Build admin login page.
- Build post list page.
- Build create/edit post form.
- Integrate Cloudinary upload.
- Add delete confirmation.
- Add admin API tests.

### Milestone 5: Search, Related Posts, and Polish

Status: Not started

Tasks:

- Implement search endpoint.
- Implement search page.
- Implement related-post backend logic.
- Add related-post UI.
- Test mobile responsiveness.
- Run build/lint/test checks.

## Chat Summaries

### 2026-08-05

- Confirmed we are building an MVP first, not the full future website.
- Agreed to discuss each major layer before coding.
- Reviewed local project structure and key files.
- Confirmed the codebase is early but well structured.
- Decided to maintain this tracker for project continuity.
- Locked Milestone 0 decisions: public UI uses Ideas, product/code direction uses Post, batch upload moves to V1.5, root README should stay short, long planning lives in `docs/PLANNING.md`.
- Updated root README, added MVP direction to planning doc, and updated tracker.
- Renamed active backend domain from `Photo` to `Post`: entity, repository, not-found exception, join tables, and cover fields.
- Verification note: compile/tests were not run from Codex because `mvn` and `docker` are not available in this shell. Verify from IntelliJ Maven panel before pushing.

### 2026-08-06

- Started Milestone 1A: Data Model for V1 Posts.
- Added V1 Post fields: `title`, `contentType`, `tags`, `pinterestUrl`, `externalUrl`, and `affiliateUrl`.
- Chose simple string tags for V1 instead of a normalized tag table; revisit normalization when tag filtering/admin tag management becomes real.
- Chose initial `ContentType` enum values: `IMAGE`, `ARTICLE`, `VIDEO`, `PRODUCT`, `OTHER`.
- Added a new Flyway migration rather than rewriting the initial schema.
- Updated repository search to include `title` and `tags`.
- Verification note: Codex attempted IntelliJ bundled Maven with a writable local repo and network access, but this sandbox failed during compile with `Access is denied` while reading downloaded dependency jars. Re-run Maven tests from IntelliJ.
- Continued Milestone 1B: public Post read API.
- Chose first public API shape: `GET /api/posts` for the feed and `GET /api/posts/{slug}` for detail.
- Added public response DTOs for post summaries, post details, collections, and moods.
- Added `PostService` with read mapping and simple related-post ordering: same place/date, then shared collections, then shared moods, capped at 8.
- Added `PostController` for public list/detail endpoints.
- Added service and controller tests.
- Added `.http` examples in `backend/requests/hourblue-api.http`.
- Verification note: Codex attempted Maven again with IntelliJ's bundled JBR, but this sandbox still fails during real compilation with `Access is denied` while reading dependency jars. Run `mvn clean test` from your normal IntelliJ/PowerShell setup before committing.

## Next Step

Run Maven verification locally, then commit Milestone 1B. After that, continue with frontend mock/public browsing or admin create/edit API discussion.
