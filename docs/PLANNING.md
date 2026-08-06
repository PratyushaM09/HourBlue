# HourBlue — Project Planning Document

*A living document, built section by section. Each section is reviewed and confirmed before the next begins.*

---

## Current MVP Direction

HourBlue is now being built as a Pinterest-adjacent visual discovery MVP, not as a full future platform in one pass.

The MVP should prove one core loop:

```txt
visitor lands on HourBlue
-> browses visual ideas
-> opens one idea/post
-> sees related content
-> continues exploring
```

Current locked decisions:

- Public UI language: use "Ideas" for the visitor-facing experience.
- Code/domain direction: use "Post" as the broader content concept.
- Backend naming: renamed the active backend domain from `Photo` to `Post` while the project is still early.
- Public route direction: prefer `/post/[slug]` over `/memory/[slug]`.
- Batch upload: defer to V1.5 unless V1 finishes early.
- Monetization: V1 is link-ready through optional Pinterest, external, and affiliate URLs, but does not include payments.
- Root README: keep short; long planning belongs in this file.

---

## Status
- [x] Name finalized: **HourBlue**
- [x] Repo created
- [x] 1. Requirements & Scope
- [x] 2. Users & User Journey
- [x] 3. Feature List (finalized specs)
- [x] 4. Data Model / DB Design
- [x] 5. Backend API Design
- [x] 6. Architecture
- [x] 7. Frontend / UI Design
- [x] 8. Implementation Roadmap
- [x] 9. Authentication & Security
- [x] 10. Error Handling Strategy
- [x] 11. Testing Strategy
- [x] 12. Deployment & CI/CD
- [x] 13. Best Practices & Coding Standards
- [x] Appendix A — Reference Framework Adaptation Note

✅ **Planning complete**

---

## 1. Requirements & Scope

### 1.1 One-line description
HourBlue is a personal visual journal — a curated, exploratory alternative to a Pinterest board, where a single photo collection can be browsed through multiple lenses (collection, mood, and later place/time/weather) instead of one flat feed.

### 1.2 Who this is for
- **Primary audience:** public visitors discovering the site (friends, portfolio reviewers, potential employers, general visitors from shared links)
- **Secondary user:** you, as the sole content owner/admin, uploading and organizing photos

There are no end-user accounts in v1 — every visitor sees the same read-only experience.

### 1.3 Functional requirements (v1)

| # | Requirement |
|---|---|
| FR1 | Visitor can land on a Home page with a featured hero image and a single clear call-to-action into the site |
| FR2 | Visitor can browse **Collections** (e.g. Skies, Moon, Cafés) as a grid, and open a collection to see its photos |
| FR3 | Visitor can browse via **Mood Explorer** (e.g. Peaceful, Adventure, Rainy) — clicking a mood shows matching photos regardless of collection |
| FR4 | Visitor can open an **individual memory page** for any photo: full image, place, date, time, weather, a short (1–2 sentence) caption, and a strip of related photos ("more from this evening/place") |
| FR5 | Visitor can **search** by free text across captions, tags, collection names, and place names |
| FR6 | Admin (you) can **log in** to a protected admin area |
| FR7 | Admin can upload a **single photo or a batch of photos at once** (e.g. migrating existing Pinterest boards), and attach to each: one or more collections, one or more moods, place, date, time, weather, and a caption. Batch upload should support setting shared metadata (e.g. same collection/place) across multiple photos at once, then per-photo overrides for caption/mood |
| FR8 | Admin can **edit or delete** an existing photo's metadata or remove the photo |
| FR9 | Site is fully responsive (mobile-first, since most visitors will land via shared links on phones) |

### 1.4 Non-functional requirements

| # | Requirement | Notes |
|---|---|---|
| NFR1 | **Performance** | Image-heavy site — must lazy-load offscreen images, serve responsively sized images, avoid layout shift while loading |
| NFR2 | **Security** | Admin routes protected by authentication; public routes are read-only with no way to reach write endpoints |
| NFR3 | **Availability** | Should run reliably on a low/no-cost hosting tier for v1 (no requirement for high-availability infra yet). No hosting preference yet — Claude will propose sensible free/cheap defaults during the Architecture section |
| NFR4 | **Maintainability** | Solo-developer project — code and structure should stay simple enough to pick back up after weeks away |
| NFR5 | **SEO (basic)** | Individual memory pages and collections should have real URLs and basic meta tags, so links are shareable and somewhat discoverable — not full SEO strategy yet |
| NFR6 | **Accessibility (basic)** | Alt text on images, sufficient color contrast, keyboard-navigable nav — not a full WCAG audit for v1 |
| NFR7 | **Data portability** | Photo metadata stored in a way that's easy to export/migrate later (plain relational schema, not locked into a proprietary format) |

### 1.5 Explicit non-goals for v1
*(Deliberately deferred — not because they're bad ideas, but to ship a solid core first)*

- Map / Timeline / Weather / Season / Time-of-day explorers
- "Similar Memories" recommendation engine
- Public user accounts, favorites, saved collections
- Stories, wallpapers, downloadable packs
- Personal stats page
- AI-powered natural language search
- Monetization (ads, affiliate links, paid content)
- Mobile app

These remain in the roadmap for Phase 2+ but are out of scope until v1 is live and stable.

### 1.6 Constraints
- Solo developer; backend skill is strong (Java/Spring Boot), frontend is a growth area
- Database: **MySQL** (not Postgres)
- Frontend: **Next.js** (App Router)
- Hosting budget: minimal/free-tier initially (Railway/Render + a free image CDN tier)
- This is both a portfolio piece and a real product intended for ongoing use — code quality and structure should reflect that (not throwaway prototype code)

---

## 2. Users & User Journey

### 2.1 User types

| User | Description | Access |
|---|---|---|
| **Visitor** | Anyone landing on the site — friend, portfolio reviewer, recruiter, random discoverer via a shared link | Read-only, no login |
| **Admin (you)** | Sole content owner | Full access to upload/edit/delete via protected admin panel |

No public accounts, favorites, or comments in v1 — every visitor gets the identical, anonymous, read-only experience. This keeps auth surface area small (only one login exists in the whole system).

### 2.2 Primary visitor journey — "First-time discovery"

This is the journey that matters most, since most traffic will arrive cold via a shared link (Instagram bio, portfolio, word of mouth).

```
1. Land on Home
   → Full-bleed hero photo, name/tagline, single "Begin Exploring" CTA
   → No nav clutter, no login prompt, no popups

2. Click "Begin Exploring" → Explore hub
   → Two clear paths shown: "Browse by Collection" / "Browse by Mood"
   → (This is the moment that differentiates HourBlue from Pinterest —
      make the choice itself feel intentional, not just another menu)

3a. Path: Collections                    3b. Path: Mood
    → Grid of named collections              → Grid of mood tiles
      (Skies, Moon, Cafés...)                   (Peaceful, Adventure, Rainy...)
    → Click one → grid of photos              → Click one → grid of photos
      within that collection                    across all collections

4. Click any photo → Individual memory page
   → Large image, place/date/time/weather, short caption
   → Scroll down → "More from this evening" strip (related photos)

5. Click a related photo → repeat step 4
   → This loop (photo → related → photo → related) is the core
     "fall in love with one, discover a hundred more" engagement loop

6. Visitor either:
   a) Keeps looping through related photos (ideal outcome)
   b) Goes back to Explore and tries the other path (Collection vs Mood)
   c) Uses Search if they have something specific in mind
   d) Leaves — no forced re-engagement (no email capture, no popup)
```

### 2.3 Secondary visitor journey — "Returning to search for something specific"

```
1. Land on Home (or any page — search is globally accessible)
2. Use Search bar → type a term (e.g. "Jaipur", "rain", "moon")
3. Instant/fast results as a photo grid
4. Click a result → Individual memory page (same as primary journey step 4 onward)
```

### 2.4 Admin journey — "Adding new memories"

```
1. Navigate to /admin, log in
2. Choose: Single upload OR Batch upload
   
   Single upload:
   → Upload one image
   → Fill in: collection(s), mood(s), place, date, time, weather, caption
   → Save → photo goes live immediately

   Batch upload:
   → Upload multiple images at once (e.g. one Pinterest board export)
   → Set shared metadata once (e.g. same place + date + collection
     for the whole batch)
   → Optionally override per-photo fields (caption, individual moods)
     before final save
   → Save → all photos go live

3. Admin can view a management list of all photos
   → Edit metadata or delete any existing photo
```

### 2.5 Key journey principles carried into design/architecture
- **Zero friction to first delight**: Home → one click → looking at real photos. No login walls, no forced onboarding.
- **The related-photos strip is the retention engine** — it deserves the most design/engineering attention of any single component, since it's what turns "one photo" into "a hundred more."
- **Two discovery paths from Explore, not one** — Collections (structured) and Mood (associative) should feel like genuinely different browsing experiences, not the same grid with a different label.
- **Admin batch upload is a first-class flow**, not an afterthought — since your existing Pinterest content needs a real migration path on day one.

### 2.6 Launch readiness rule
Soft-launch once at least **6 collections** and **4 moods** are populated with real content (not placeholders) — enough that Explore never shows an empty or half-broken-looking grid. Full Pinterest migration can continue after launch; the site should feel intentional from day one even if it's smaller than the eventual archive.

---

## 3. Feature List (Finalized Specs)

Each feature below is specified concretely enough to drive the data model and API design in the next sections.

### 3.1 Home
- Full-bleed hero image (randomly selected from a curated "featured" pool, not literally random from all photos — admin can flag photos as `is_featured`)
- Overlay: name/site title, one-line tagline, single primary CTA button ("Begin Exploring")
- Below the fold (optional, revealed on scroll, not required for launch): 3–4 recently added photos as a teaser strip
- No navigation bar cluttering the first screen — a minimal, semi-transparent nav appears after the hero (logo/name + Search icon + Explore link)

### 3.2 Explore (hub page)
- Two large, visually distinct cards: **"Browse by Collection"** and **"Browse by Mood"**
- Each card uses a representative background photo, not a generic icon
- No other content on this page — it's a deliberate fork, not a dashboard

### 3.3 Collections
- **Listing page:** grid of collection cards, each showing: cover photo (admin-selectable, defaults to most recent photo in that collection), collection name, photo count
- **Collection detail page:** masonry/grid of that collection's photos, infinite scroll (load more on scroll, not pagination buttons — feels more "endless discovery")
- Sort order within a collection: newest first by default
- Collections are admin-defined (not user-generated), simple flat list (no nested sub-collections in v1)

### 3.4 Mood Explorer
- **Listing page:** grid of mood tiles — each mood has an admin-set representative photo and name (Peaceful, Adventure, Rainy, etc.)
- **Mood detail page:** same grid/infinite-scroll behavior as Collections, but pulls photos tagged with that mood **across all collections**
- A photo can belong to multiple moods (many-to-many)
- v1 mood list (fixed, admin can add more later via a simple lookup table, not hardcoded in code): Peaceful, Adventure, Rainy, Golden, Quiet, Joyful — final list confirmed when we do the data model, but structured so adding a 7th mood later is a DB insert, not a code change

### 3.5 Individual Memory Page
- Large image (full width on mobile, constrained max-width with padding on desktop)
- Metadata block below image: place name, date, time, weather — displayed only if present (no "N/A" clutter; omit blank fields entirely)
- Caption: 1–2 sentences, plain text, no rich formatting needed in v1
- **Related photos strip** ("More from this evening / this place"): horizontally scrollable row, populated using this priority order:
  1. Other photos from the same **place + same date** (same "outing")
  2. If fewer than 4 results, fill remainder with other photos sharing at least one **collection**
  3. If still fewer than 4, fill remainder with other photos sharing at least one **mood**
  4. Cap at 6–8 related photos shown
- Clicking any related photo navigates to *that* photo's memory page (loop continues)
- Real, shareable URL per photo (slug-based, e.g. `/memory/jaipur-golden-hour-june-2026`, not a raw numeric ID) — supports NFR5 (basic SEO/shareability)

### 3.6 Search
- Single search bar, accessible from every page (in the persistent minimal nav)
- Searches across: caption text, place name, collection name(s), mood name(s)
- Results shown as a live-updating photo grid as the user types (debounced, not requiring Enter) — falls back to Enter-to-search if live search proves too heavy at implementation time
- Empty state: friendly message + suggested collections/moods to browse instead (never a dead end)

### 3.7 Admin Panel
**Auth**
- Single admin account (no public registration flow — credentials set directly via database/environment, not a signup page)
- Session-based or JWT login, admin routes fully separated from public routes at the routing level, not just hidden in the UI

**Single upload**
- Upload one image
- Form fields: collection(s) [multi-select], mood(s) [multi-select], place, date, time, weather [dropdown: Sunny/Cloudy/Foggy/Rainy/Storm], caption, `is_featured` toggle
- Preview before save

**Batch upload**
- Upload multiple images in one action (drag-and-drop, multi-file)
- Step 1: set **shared metadata** applied to all (place, date, collection, weather — fields most likely identical across one outing's photos)
- Step 2: per-photo review grid — override caption and mood per individual photo before final confirm
- Single "Publish batch" action commits all at once (not photo-by-photo saves, to avoid a half-published batch if something fails midway)

**Management**
- Table/list view of all photos: thumbnail, collection(s), mood(s), place, date, featured flag
- Edit any field inline or via a detail form
- Delete with a confirmation step (no accidental permanent deletes)
- Basic filter/search within the admin list itself (reuse the public search logic where possible)

### 3.8 Cross-cutting feature notes
- **Image handling:** every uploaded photo generates multiple responsive sizes (thumbnail for grids, medium for related-strip, full for memory page) — handled by the storage/CDN layer (Cloudinary), not custom backend resizing code
- **Slugs:** every collection, mood, and photo gets a URL-safe slug generated at creation time (from name/place/date), with a uniqueness check (append a suffix if a collision occurs)
- **Deletion:** hard delete in v1 (simpler) — acceptable risk given NFR7 is about portability, not backups

---

## 4. Data Model / DB Design

Relational schema (MySQL 8). Designed so the related-photos query (Section 3.5) is cheap, and adding moods/collections later never requires a schema change.

### 4.1 Entity overview

```
photo
├── belongs to many → collection    (via photo_collection)
├── belongs to many → mood          (via photo_mood)
└── has one → place (denormalized fields, not a separate table in v1)

collection   — admin-defined, flat list
mood         — admin-defined, flat list
admin_user   — single row in practice, but modeled as a real table
```

Why no separate `place` table in v1: places aren't reused with strict identity in the same way collections/moods are (Jaipur written once vs. reused exactly) — but to keep the "same place + same date = same outing" query cheap and avoid typo-splitting (e.g. "Jaipur" vs "jaipur"), place name will be normalized (trimmed, lowercased for comparison) at the application layer before insert. A dedicated `place` table becomes worthwhile if/when Map (Phase 2) needs coordinates — deferred until then.

### 4.2 Tables

**`photo`**
| Column | Type | Notes |
|---|---|---|
| id | BIGINT, PK, auto-increment | |
| slug | VARCHAR(255), UNIQUE, NOT NULL | URL-safe, generated at creation |
| image_url | VARCHAR(500), NOT NULL | Cloudinary full-size URL |
| thumbnail_url | VARCHAR(500), NOT NULL | Cloudinary transform URL |
| medium_url | VARCHAR(500), NOT NULL | Cloudinary transform URL |
| caption | VARCHAR(500), NULLABLE | |
| place | VARCHAR(255), NULLABLE | Free text, normalized on write |
| captured_date | DATE, NULLABLE | |
| captured_time | TIME, NULLABLE | |
| weather | ENUM('SUNNY','CLOUDY','FOGGY','RAINY','STORM'), NULLABLE | |
| is_featured | BOOLEAN, DEFAULT FALSE | |
| created_at | TIMESTAMP, DEFAULT CURRENT_TIMESTAMP | |
| updated_at | TIMESTAMP, ON UPDATE CURRENT_TIMESTAMP | |

Indexes: `INDEX(place, captured_date)` — directly supports the #1 related-photos priority (same place + same date). `INDEX(captured_date)` for timeline-readiness later. `FULLTEXT INDEX(caption, place)` for Search (Section 3.6).

**`collection`**
| Column | Type | Notes |
|---|---|---|
| id | BIGINT, PK | |
| name | VARCHAR(100), UNIQUE, NOT NULL | e.g. "Skies" |
| slug | VARCHAR(120), UNIQUE, NOT NULL | |
| cover_photo_id | BIGINT, NULLABLE, FK → photo.id | Admin-selectable; nullable so a collection can exist before a cover is chosen |
| display_order | INT, DEFAULT 0 | Lets admin control listing order manually if desired |

**`mood`**
| Column | Type | Notes |
|---|---|---|
| id | BIGINT, PK | |
| name | VARCHAR(100), UNIQUE, NOT NULL | e.g. "Peaceful" |
| slug | VARCHAR(120), UNIQUE, NOT NULL | |
| cover_photo_id | BIGINT, NULLABLE, FK → photo.id | |
| display_order | INT, DEFAULT 0 | |

**`photo_collection`** (join table, many-to-many)
| Column | Type |
|---|---|
| photo_id | BIGINT, FK → photo.id, PK part |
| collection_id | BIGINT, FK → collection.id, PK part |

Composite PK `(photo_id, collection_id)`. Directly supports related-photos priority #2 (shared collection).

**`photo_mood`** (join table, many-to-many)
| Column | Type |
|---|---|
| photo_id | BIGINT, FK → photo.id, PK part |
| mood_id | BIGINT, FK → mood.id, PK part |

Composite PK `(photo_id, mood_id)`. Supports related-photos priority #3.

**`admin_user`**
| Column | Type | Notes |
|---|---|---|
| id | BIGINT, PK | |
| username | VARCHAR(100), UNIQUE, NOT NULL | |
| password_hash | VARCHAR(255), NOT NULL | BCrypt via Spring Security |
| created_at | TIMESTAMP | |

Modeled as a real table (not hardcoded credentials) so Spring Security's standard auth flow applies cleanly, and a second admin (e.g. future collaborator) is just a row away — even though v1 has exactly one row.

### 4.3 Why join tables instead of a JSON column
MySQL 8 does support JSON columns, and it's tempting to store `mood_ids: [1,2,3]` directly on `photo`. Deliberately **not** doing that here, because:
- The related-photos query needs to efficiently find "other photos sharing mood X" — that's a fast indexed join with a proper join table, but requires a full-table JSON scan otherwise
- Standard relational joins are simpler for a Java/Spring Data JPA developer to reason about and debug than JSON querying
- Admin edits (add/remove one mood from one photo) are cleaner as single-row inserts/deletes in a join table than JSON array manipulation

### 4.4 Related-photos query strategy (supports Section 3.5)
Executed as up to three sequential queries, short-circuiting once 6-8 results are gathered:
1. SELECT photos WHERE place = ? AND captured_date = ? AND id != ? LIMIT 8
2. If needed: photos sharing any collection_id from photo_collection where current photo's collections match
3. If needed: photos sharing any mood_id from photo_mood where current photo's moods match

This stays index-friendly and avoids needing a recommendation engine for v1 - "Similar Memories" (Phase 2, vector/embedding-based) can replace this later without changing the schema.

### 4.5 Slug generation rule
- Photo slug: {place-slugified}-{caption-first-few-words-or-collection}-{month}-{year}, fallback to memory-{id} if insufficient text is available
- Collection/mood slugs: straightforward name slugification (lowercase, hyphenated)
- Collision handling: append -2, -3, etc. on conflict, checked at insert time

---

## 5. Backend API Design

REST API, Spring Boot. Public endpoints are read-only; admin endpoints require authentication. All list endpoints support pagination via `page`/`size` query params (cursor-based isn't necessary at this scale).

### 5.1 Public endpoints (no auth)

| Method | Path | Purpose |
|---|---|---|
| GET | /api/home | Featured photo(s) + recent teaser strip |
| GET | /api/collections | List all collections (name, slug, cover, photo count) |
| GET | /api/collections/{slug} | Collection detail + paginated photos |
| GET | /api/moods | List all moods |
| GET | /api/moods/{slug} | Mood detail + paginated photos |
| GET | /api/photos/{slug} | Single photo detail incl. metadata |
| GET | /api/photos/{slug}/related | Related photos (Section 4.4 logic) |
| GET | /api/search?q={query} | Search across caption/place/collection/mood |

### 5.2 Admin endpoints (auth required)

| Method | Path | Purpose |
|---|---|---|
| POST | /api/auth/login | Admin login, returns JWT/session |
| POST | /api/admin/photos | Create single photo (multipart: image + metadata) |
| POST | /api/admin/photos/batch | Create multiple photos: shared metadata + per-photo overrides |
| PUT | /api/admin/photos/{id} | Update photo metadata |
| DELETE | /api/admin/photos/{id} | Delete photo |
| GET | /api/admin/photos | Paginated list for management table (all photos, not just public-visible) |
| POST | /api/admin/collections | Create collection |
| PUT | /api/admin/collections/{id} | Update collection (incl. cover_photo_id) |
| POST | /api/admin/moods | Create mood |
| PUT | /api/admin/moods/{id} | Update mood |

### 5.3 Auth approach
JWT-based (stateless), issued on login, sent as `Authorization: Bearer {token}` on admin requests. Chosen over session cookies because Next.js frontend and Spring Boot backend are separate origins/deployments — stateless JWT avoids CORS/cookie complications across domains. Spring Security filter chain rejects any `/api/admin/**` request without a valid token; public `/api/**` routes remain open.

### 5.4 Response shape conventions
- Consistent envelope for list endpoints: `{ "data": [...], "page": 0, "size": 20, "totalElements": N, "totalPages": N }`
- Single-resource endpoints return the resource directly (no envelope needed)
- Errors: consistent shape `{ "error": "message", "status": 404 }`, standard HTTP status codes (404 for not found, 401/403 for auth, 400 for validation)

### 5.5 Image upload flow
1. Frontend uploads raw image file(s) directly to Cloudinary via a signed upload (signature generated by backend, so Cloudinary credentials never touch the frontend)
2. Cloudinary returns the resulting URLs (original + auto-generated responsive transforms)
3. Frontend sends those URLs + metadata to `/api/admin/photos` (or `/batch`) as JSON — the backend never handles raw image bytes, keeping it lightweight

---

## 6. Architecture

### 6.1 System overview

```
┌─────────────────┐        ┌──────────────────┐        ┌─────────────┐
│   Next.js app    │  API   │  Spring Boot API  │  JDBC  │   MySQL 8   │
│  (Vercel)         │ ─────>│  (Railway/Render)  │ ─────>│  (managed)  │
└─────────────────┘  calls  └──────────────────┘        └─────────────┘
        │                            │
        │  signed upload             │  signature request
        ▼                            ▼
┌──────────────────────────────────────────┐
│              Cloudinary (images)          │
└──────────────────────────────────────────┘
```

### 6.2 Component choices & reasoning

| Component | Choice | Why |
|---|---|---|
| Frontend hosting | **Vercel** | Built by the Next.js team, zero-config deploys, generous free tier, automatic image/CDN handling for anything not routed through Cloudinary |
| Backend hosting | **Railway** (Render as backup option) | Simple Spring Boot deploys from a Dockerfile, free/low-cost tier sufficient for v1 traffic, easy managed MySQL add-on |
| Database | **MySQL 8** (Railway-managed) | Per your existing knowledge; managed instance avoids self-hosting ops burden |
| Image storage/CDN | **Cloudinary** | Free tier covers a personal project's volume; automatic responsive transforms remove the need for custom image-resizing backend code (directly supports NFR1) |
| Auth | **Spring Security + JWT** | Standard, well-documented for Spring Boot; stateless fits the separate frontend/backend origin setup |

### 6.3 Repo structure (monorepo, as discussed)

```
hourblue/
├── frontend/                 # Next.js app
│   ├── app/                  # App Router pages
│   ├── components/
│   ├── lib/                  # API client, utils
│   └── public/
├── backend/                  # Spring Boot app
│   ├── src/main/java/.../hourblue/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   └── config/           # Security, CORS config
│   └── src/main/resources/
│       └── application.yml
├── docker-compose.yml         # local dev: backend + MySQL together
└── README.md
```

### 6.4 Environments
- **Local dev:** `docker-compose up` runs MySQL + backend locally; frontend runs via `npm run dev` pointed at local backend URL via env variable
- **Production:** frontend on Vercel, backend on Railway, MySQL on Railway's managed add-on — each configured via environment variables (`NEXT_PUBLIC_API_URL`, DB credentials, Cloudinary keys, JWT secret), never committed to the repo

### 6.5 CORS
Backend explicitly allows only the deployed frontend origin (and `localhost:3000` for local dev) in Spring Security's CORS config — not a wildcard `*`, since admin endpoints exist on the same API.

### 6.6 Why not a single full-stack Next.js app (skip separate Spring Boot backend)?
Considered, since Next.js API routes *could* host the backend logic too. Sticking with a separate Spring Boot backend because:
- It's your strongest skill area — better code quality and less time lost to unfamiliar patterns
- Cleanly separating frontend/backend matches how real production teams are structured, which is useful for the portfolio angle
- Keeps the option open to add other frontends later (e.g. a future admin-only desktop tool) without backend rework

---

## 7. Frontend / UI Design

### 7.1 Visual language
- **Mood:** quiet, editorial, photography-led — closer to a minimal photography portfolio or a print magazine than a typical web app. Images do the talking; UI chrome stays out of the way.
- **Color palette:** near-neutral base (off-white / warm charcoal for light/dark text) with **blue-hour dusk tones** (deep indigo/blue) as the single accent color, used sparingly (links, active states, the CTA button) — echoes the site name without becoming a "blue everything" theme
- **Typography:** one serif or high-contrast display font for titles/hero text (editorial feel), one clean sans-serif for body/UI text (e.g. captions, nav). Avoid more than two font families.
- **Spacing:** generous whitespace/negative space around images — images should feel presented, not packed into a dense grid like Pinterest
- **Motion:** subtle fade/slide transitions between pages and on image load (Framer Motion), never flashy — motion should feel calm, matching the "quiet" brand feeling

### 7.2 Page-by-page component breakdown

**Home (`/`)**
- `<HeroSection>` — full-bleed image, overlay text, CTA button
- `<MinimalNav>` — appears after hero scroll, or fixed-transparent from the start
- `<RecentTeaserStrip>` (optional for launch)

**Explore (`/explore`)**
- `<ExploreForkCard>` × 2 (Collection path / Mood path)

**Collections (`/collections`, `/collections/[slug]`)**
- `<CollectionGrid>` — reusable, also used for Mood listing
- `<CollectionCard>` — cover photo, name, count
- `<PhotoMasonryGrid>` — reusable, also used for Mood detail, Search results
- `<InfiniteScrollLoader>`

**Mood Explorer (`/moods`, `/moods/[slug]`)**
- Reuses `<CollectionGrid>` and `<PhotoMasonryGrid>` from above (same component, different data source — this is the "different browsing feel, same underlying grid tech" principle from Section 2.5, achieved via distinct tile styling rather than separate components)

**Individual Memory (`/memory/[slug]`)**
- `<PhotoDisplay>` — large image, responsive
- `<MetadataBlock>` — place/date/time/weather, conditionally rendered fields
- `<RelatedPhotosStrip>` — horizontal scroll, most-engineered component per Section 2.5

**Search (`/search`)**
- `<SearchBar>` — lives in `<MinimalNav>`, also has a dedicated results page
- Reuses `<PhotoMasonryGrid>` for results
- `<EmptySearchState>` — suggested collections/moods fallback

**Admin (`/admin/*`, separate route group)**
- `<LoginForm>`
- `<SingleUploadForm>`
- `<BatchUploadWizard>` — step 1 (shared metadata) → step 2 (per-photo review grid)
- `<PhotoManagementTable>` — with inline edit/delete

### 7.3 Responsive behavior
- Mobile-first: single-column photo grids on small screens, expanding to masonry/multi-column at tablet+ breakpoints (Tailwind `sm:`/`md:`/`lg:` breakpoints)
- Related-photos strip: horizontally swipeable on mobile (native scroll + snap), hoverable/scrollable row on desktop
- Admin panel: functional on mobile but optimized for desktop use, since you'll likely upload from a computer

### 7.4 Component reuse strategy
Minimizing component count deliberately, since this is a solo-maintained project:
- One `<PhotoMasonryGrid>` powers Collections detail, Mood detail, and Search results
- One `<CollectionGrid>` powers both Collections listing and Mood listing (tile content/styling driven by props, not separate components)
- One `<PhotoCard>` used inside every grid context

---

## 8. Implementation Roadmap

Ordered to get something real and clickable as early as possible, then layer in complexity.

### Milestone 1 — Skeleton
- Repo structure set up (frontend/backend/docker-compose)
- Spring Boot project bootstrapped, connects to local MySQL via Docker
- Core schema created (Section 4) via migration (Flyway recommended for tracked, repeatable schema changes)
- Next.js project bootstrapped, Tailwind configured, basic routing in place
- Cloudinary account set up, signed upload flow working end-to-end (upload one test image successfully)

### Milestone 2 — Admin can populate content
- Admin auth (login) working
- Single photo upload (form → API → DB) working
- Collection/mood CRUD (minimal, can be admin-only DB inserts initially if faster, then a real UI)
- **Goal:** you can manually add enough photos to hit the Section 2.6 launch minimum (6 collections, 4 moods)

### Milestone 3 — Public browsing core
- Home page (hero + CTA)
- Explore hub
- Collections listing + detail (with real data from Milestone 2)
- Mood listing + detail
- Individual memory page (without related-strip yet)

### Milestone 4 — The differentiator
- Related-photos query + `<RelatedPhotosStrip>` component
- This is the most important milestone for the product's actual thesis — don't rush it

### Milestone 5 — Search + polish
- Search endpoint + UI
- Responsive pass across all pages
- Empty states, loading states, error states
- Basic SEO meta tags per page

### Milestone 6 — Batch upload + real migration
- Batch upload wizard (shared metadata + per-photo review)
- Actually migrate your Pinterest boards into HourBlue using it
- Reach the launch minimum for real

### Milestone 7 — Deploy & launch
- Deploy backend to Railway, frontend to Vercel, connect production Cloudinary/DB
- Final QA pass on mobile
- Soft launch

### Milestone 8 — Phase 2 (post-launch)
- Map, Timeline, Weather/Season/Time filters, Similar Memories, Stats page — revisit priority based on what you enjoy building/using most once v1 is live

---

*End of initial planning pass. This document should be updated as decisions evolve during implementation.*

## 9. Authentication & Security

Scoped to what a single-admin, no-public-accounts site actually needs — not enterprise auth infrastructure.

### 9.1 Authentication
- **JWT-based**, issued on `/api/auth/login`, short-lived access token (e.g. 1 hour) — acceptable for a single admin who logs in occasionally, so no refresh-token rotation complexity is needed for v1. If sessions expire mid-upload, re-login is a minor inconvenience, not a UX blocker worth engineering around yet.
- Password stored via **BCrypt** hash (Spring Security default), never plaintext, never logged.
- No public registration endpoint exists at all — the admin row is created once via a database seed/migration, not through the app.

### 9.2 Authorization
- Two effective zones: public (`/api/**` minus `/api/admin/**`) and admin-only (`/api/admin/**`). No role hierarchy needed since there's exactly one privilege level — full role-based access control (RBAC) would be over-engineering here, but the code structure (a `Role` concept on `admin_user`) leaves room to add a second role later without a rewrite.

### 9.3 CORS & CSRF
- CORS: only the deployed frontend origin + localhost dev origin allowed (Section 6.5)
- CSRF: not applicable in the traditional sense since auth is via `Authorization` header (JWT), not cookies — CSRF protection matters most for cookie-based sessions, which this isn't

### 9.4 Input validation
- All admin write endpoints validate via Spring's `@Valid` + Bean Validation annotations (e.g. `@NotBlank`, `@Size`, `@Past` for dates) — rejects malformed data before it reaches the database
- File upload validation: restrict accepted MIME types to image formats (JPEG/PNG/WebP), enforce a max file size (e.g. 15MB) before accepting an upload

### 9.5 Common web vulnerabilities
- **SQL injection:** non-issue by default — Spring Data JPA/Hibernate uses parameterized queries throughout; raw SQL is avoided
- **XSS:** captions/place names are plain text rendered by React, which escapes content by default — no `dangerouslySetInnerHTML` used anywhere in v1
- **Secure headers:** standard set via Spring Security (X-Content-Type-Options, X-Frame-Options) — no need for a dedicated headers library at this scale

### 9.6 Media security
- Cloudinary signed uploads (Section 5.5) mean upload credentials never reach the browser
- Public photo URLs are intentionally public (this is a public gallery) — no signed/expiring URLs needed for viewing, only for the upload step

---

## 10. Error Handling Strategy

### 10.1 Backend
- Centralized `@ControllerAdvice` exception handler in Spring Boot — catches exceptions once, returns the consistent error envelope from Section 5.4, rather than scattering try/catch blocks across controllers
- Distinct exception types mapped to correct status codes: `PhotoNotFoundException` → 404, `ValidationException` → 400, `UnauthorizedException` → 401
- Server errors (500) are logged with full stack trace server-side, but the client only ever receives a generic safe message — no internal details (stack traces, SQL errors) leak to the frontend

### 10.2 Frontend
- API client wraps all requests; failed requests surface a friendly inline message ("Couldn't load this collection — try again") rather than a blank page or raw error
- Individual memory page / collection page: if a slug doesn't resolve, show a proper 404 page with a link back to Explore, not a broken layout
- Admin panel: form-level validation errors shown inline next to the relevant field, not just a generic toast

---

## 11. Testing Strategy

Scoped for a solo developer — enough coverage to catch real regressions without turning testing into a second full-time job.

| Layer | Approach | Priority |
|---|---|---|
| Backend unit tests | JUnit 5 + Mockito for service-layer logic — especially the related-photos query logic (Section 4.4), since it's the most important behavior in the app | High |
| Backend integration tests | Spring Boot Test + Testcontainers (spins up a real MySQL in Docker for tests) for repository/API-level tests — verifies actual queries work, not just mocked behavior | Medium-High |
| API tests | A Postman/Insomnia collection (or `.http` files) covering every endpoint in Section 5 — useful both as tests and as living API documentation | Medium |
| Frontend tests | Light — component tests (React Testing Library) for the components with real logic (e.g. `<RelatedPhotosStrip>`, `<BatchUploadWizard>`); skip testing purely presentational components | Medium |
| End-to-end tests | One or two Playwright/Cypress flows covering the critical path only: "visitor lands on Home → explores → views a memory" and "admin logs in → uploads a photo → it appears publicly" | Low-Medium, nice to have before launch |
| Security/performance tests | Not building a dedicated test suite for this at solo/personal-project scale — instead, a manual pre-launch checklist (auth routes actually reject unauthenticated requests, images lazy-load, Lighthouse score check) | Manual, pre-launch only |

---

## 12. Deployment & CI/CD

### 12.1 Environments
Already defined in Section 6.4 (local via Docker Compose, production via Vercel + Railway). Adding a lightweight **staging** step is optional for a solo project — Vercel's preview deployments (automatic per-branch/PR) effectively serve as staging for the frontend at no extra setup cost. Railway also supports preview environments if needed later.

### 12.2 CI/CD pipeline (GitHub Actions)
```
On push to a feature branch:
  → run backend tests (Maven/Gradle)
  → run frontend build + lint
  → (Vercel auto-deploys a preview URL for the branch)

On merge to main:
  → run full test suite
  → build backend Docker image
  → deploy backend to Railway
  → (Vercel auto-deploys production frontend)
```
Kept intentionally simple — no blue-green deploys, no canary releases, no multi-region rollout. Those solve problems this project doesn't have.

### 12.3 Database migrations
- **Flyway** manages schema changes as versioned SQL files checked into the repo (`V1__init_schema.sql`, `V2__add_is_featured.sql`, etc.)
- Migrations run automatically on backend startup against whichever DB it's connected to (local, staging, prod) — never manual schema edits via a GUI, which would drift local/prod apart

### 12.4 Monitoring & logging
- Railway/Vercel built-in logs and metrics are sufficient at this scale — no need for a dedicated observability stack (e.g. Datadog, ELK) for a solo personal project
- Structured logging (SLF4J) on the backend for key events (photo upload, auth failures) — enough to debug issues after the fact without a full APM tool

---

## 13. Best Practices & Coding Standards

### 13.1 General principles
- **KISS over SOLID-for-its-own-sake**: apply SOLID principles where they genuinely reduce complexity (e.g. separating `PhotoService` from `PhotoController`), not as ceremony — a solo project doesn't need interface-per-class abstraction if there's only ever one implementation
- **DRY, but not prematurely**: the "reuse one grid component for Collections/Mood/Search" decision (Section 7.4) is real DRY; don't abstract things that have only appeared once

### 13.2 Naming conventions
- Backend: standard Java conventions (`PascalCase` classes, `camelCase` methods/fields, `UPPER_SNAKE_CASE` constants)
- Frontend: `PascalCase` for components, `camelCase` for functions/variables, kebab-case for file names matching Next.js conventions
- Database: `snake_case` for tables/columns (matches the schema in Section 4.2)

### 13.3 Folder organization
- Backend: layered by responsibility (`controller/`, `service/`, `repository/`, `model/`) as shown in Section 6.3 — appropriate for this project's size; a feature-first structure would add navigation overhead without benefit at this module count
- Frontend: Next.js App Router's file-based routing plus a flat `components/` directory — introduce subfolders only once a category exceeds ~8-10 components

### 13.4 Git workflow
- `main` branch always deployable
- Feature branches per unit of work (e.g. `feature/related-photos-strip`, `fix/search-empty-state`)
- Commit messages: short imperative summary line (e.g. "Add related-photos query to PhotoService"), consistent enough to scan `git log` later and understand history
- No formal PR-review process needed solo, but opening PRs against `main` even alone is worth doing — it gives you a clean diff view and a paper trail per feature, and mirrors real team workflow for the portfolio angle

### 13.5 Documentation
- This planning document stays the single source of truth for product/architecture decisions, updated as things change during build
- Code-level documentation: JavaDoc on non-obvious service methods (especially the related-photos logic), README per top-level folder (`frontend/README.md`, `backend/README.md`) covering local setup steps

---

## Appendix A — Reference Framework Adaptation Note

This document borrows its phase-based rigor from a generic "Pinterest-clone, scale-to-millions" documentation template, but deliberately diverges from it in scope:

**Adopted (with solo-project sizing):** Product planning, system architecture, database design, API design, feature breakdown, security fundamentals, error handling, testing strategy, deployment/CI-CD, coding standards, UI pages, user flows, roadmap.

**Deliberately excluded, and why:**
- **Recommendation engine (collaborative filtering, cold-start strategy):** HourBlue has one content owner, not a marketplace of user-generated content needing personalization — the related-photos logic (Section 4.4) already serves the "discover more" purpose without a recommendation system
- **Notifications (email/push):** no other users exist to generate events for; nothing to notify anyone about
- **Real-time communication:** nothing in this product is live/collaborative
- **Messaging:** no user-to-user relationships exist
- **Microservices migration path, multi-region scaling, horizontal auto-scaling infra:** solving for millions of concurrent users is the wrong problem for a personal visual journal — if HourBlue ever unexpectedly goes viral, Vercel/Railway/Cloudinary's existing managed scaling covers realistic traffic spikes without any of this being pre-built
- **RBAC with multiple roles/permissions:** exactly one privilege level exists (admin); building permission matrices for roles that don't exist yet is speculative complexity

The goal throughout: match engineering effort to actual scale and actual users, not hypothetical future scale — while keeping the door open (via clean layering, not premature abstraction) to add complexity later if the project's scope genuinely grows.
