import Link from "next/link";
import Image from "next/image";
import { IdeaGrid } from "@/components/idea-grid";
import { MinimalNav } from "@/components/minimal-nav";
import { featuredPost, getCollections, getMoods, posts } from "@/lib/mock-posts";

export default function HomePage() {
  const collections = getCollections().slice(0, 4);
  const moods = getMoods().slice(0, 4);

  return (
    <>
      <MinimalNav />
      <main>
        <section className="relative min-h-[78vh] overflow-hidden">
          <Image
            src={featuredPost.imageUrl}
            alt={featuredPost.title}
            fill
            priority
            sizes="100vw"
            className="object-cover"
          />
          <div className="absolute inset-0 bg-gradient-to-r from-charcoal/80 via-charcoal/35 to-transparent" />
          <div className="relative mx-auto flex min-h-[78vh] max-w-6xl flex-col justify-end px-5 pb-16 pt-28 text-paper">
            <p className="mb-4 text-sm font-medium uppercase tracking-[0.18em] text-[#f5e7d2]">
              Visual Ideas
            </p>
            <h1 className="max-w-3xl font-display text-5xl leading-tight md:text-7xl">
              Browse one beautiful thought into the next.
            </h1>
            <p className="mt-5 max-w-xl text-base leading-7 text-paper/82 md:text-lg">
              HourBlue starts as a small, polished discovery loop: open an idea,
              follow its related moments, and keep wandering.
            </p>
            <div className="mt-8 flex flex-wrap gap-3">
              <Link
                href="#feed"
                className="rounded-full bg-paper px-5 py-3 text-sm font-semibold text-charcoal transition hover:bg-[#f5e7d2]"
              >
                Explore Ideas
              </Link>
              <Link
                href={`/ideas/${featuredPost.slug}`}
                className="rounded-full border border-paper/40 px-5 py-3 text-sm font-semibold text-paper transition hover:bg-paper hover:text-charcoal"
              >
                Open Featured
              </Link>
            </div>
          </div>
        </section>

        <section className="border-b border-charcoal/10 bg-[#f6f1ea]">
          <div className="mx-auto grid max-w-6xl gap-6 px-5 py-8 md:grid-cols-2">
            <div>
              <p className="text-sm font-semibold text-charcoal/55">Collections</p>
              <div className="mt-3 flex flex-wrap gap-2">
                {collections.map((collection) => (
                  <Link
                    key={collection.slug}
                    href={`/collections/${collection.slug}`}
                    className="rounded-full bg-white px-3 py-2 text-sm text-charcoal/75 hover:text-dusk"
                  >
                    {collection.name}
                  </Link>
                ))}
              </div>
            </div>
            <div>
              <p className="text-sm font-semibold text-charcoal/55">Moods</p>
              <div className="mt-3 flex flex-wrap gap-2">
                {moods.map((mood) => (
                  <Link
                    key={mood.slug}
                    href={`/moods/${mood.slug}`}
                    className="rounded-full bg-white px-3 py-2 text-sm text-charcoal/75 hover:text-dusk"
                  >
                    {mood.name}
                  </Link>
                ))}
              </div>
            </div>
          </div>
        </section>

        <section id="feed" className="mx-auto max-w-6xl px-5 py-12">
          <div className="mb-8 flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
            <div>
              <h2 className="font-display text-4xl text-charcoal">Latest Ideas</h2>
              <p className="mt-2 max-w-2xl text-sm leading-6 text-charcoal/65">
                A mock feed for the first public browsing pass.
              </p>
            </div>
            <Link href="/moods" className="text-sm font-medium text-dusk hover:text-dusk-dark">
              Browse by mood
            </Link>
          </div>
          <IdeaGrid posts={posts} />
        </section>
      </main>
    </>
  );
}
