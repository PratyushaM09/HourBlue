import { notFound } from "next/navigation";
import Image from "next/image";
import { MinimalNav } from "@/components/minimal-nav";
import { RelatedIdeasStrip } from "@/components/related-ideas-strip";
import { getPostBySlug, getRelatedPosts, posts } from "@/lib/mock-posts";

type IdeaDetailPageProps = {
  params: {
    slug: string;
  };
};

export function generateStaticParams() {
  return posts.map((post) => ({ slug: post.slug }));
}

export default function IdeaDetailPage({ params }: IdeaDetailPageProps) {
  const post = getPostBySlug(params.slug);

  if (!post) {
    notFound();
  }

  const relatedPosts = getRelatedPosts(post);

  return (
    <>
      <MinimalNav />
      <main className="mx-auto max-w-6xl px-5 py-10">
        <article className="grid gap-8 lg:grid-cols-[minmax(0,1.25fr)_minmax(320px,0.75fr)]">
          <div className="overflow-hidden rounded-lg border border-charcoal/10 bg-white">
            <Image
              src={post.imageUrl}
              alt={post.title}
              width={1400}
              height={1100}
              priority
              sizes="(min-width: 1024px) 60vw, 100vw"
              className="max-h-[780px] w-full object-cover"
            />
          </div>
          <aside className="space-y-8 lg:pt-6">
            <div>
              <p className="text-sm font-medium uppercase tracking-[0.16em] text-dusk">
                Idea
              </p>
              <h1 className="mt-3 font-display text-5xl leading-tight text-charcoal">
                {post.title}
              </h1>
              <p className="mt-5 text-base leading-7 text-charcoal/70">
                {post.caption}
              </p>
            </div>

            <dl className="grid grid-cols-2 gap-4 border-y border-charcoal/10 py-5 text-sm">
              <div>
                <dt className="text-charcoal/45">Place</dt>
                <dd className="mt-1 font-medium">{post.place}</dd>
              </div>
              <div>
                <dt className="text-charcoal/45">Date</dt>
                <dd className="mt-1 font-medium">{post.capturedDate}</dd>
              </div>
              {post.capturedTime && (
                <div>
                  <dt className="text-charcoal/45">Time</dt>
                  <dd className="mt-1 font-medium">{post.capturedTime}</dd>
                </div>
              )}
              {post.weather && (
                <div>
                  <dt className="text-charcoal/45">Weather</dt>
                  <dd className="mt-1 font-medium">{post.weather}</dd>
                </div>
              )}
            </dl>

            <div className="space-y-3">
              <p className="text-sm font-semibold text-charcoal/55">Moods</p>
              <div className="flex flex-wrap gap-2">
                {post.moods.map((mood) => (
                  <span key={mood} className="rounded-full bg-[#e8f0dc] px-3 py-1.5 text-sm">
                    {mood}
                  </span>
                ))}
              </div>
            </div>

            <div className="space-y-3">
              <p className="text-sm font-semibold text-charcoal/55">Links</p>
              <div className="flex flex-wrap gap-3">
                {post.pinterestUrl && (
                  <a className="text-sm font-medium text-dusk hover:text-dusk-dark" href={post.pinterestUrl}>
                    Pinterest
                  </a>
                )}
                {post.externalUrl && (
                  <a className="text-sm font-medium text-dusk hover:text-dusk-dark" href={post.externalUrl}>
                    Source
                  </a>
                )}
                {post.affiliateUrl && (
                  <a className="text-sm font-medium text-dusk hover:text-dusk-dark" href={post.affiliateUrl}>
                    Shop
                  </a>
                )}
              </div>
            </div>
          </aside>
        </article>

        <div className="mt-14">
          <RelatedIdeasStrip posts={relatedPosts} />
        </div>
      </main>
    </>
  );
}
