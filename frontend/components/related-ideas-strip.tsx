import Link from "next/link";
import Image from "next/image";
import type { Post } from "@/lib/mock-posts";

type RelatedIdeasStripProps = {
  posts: Post[];
};

export function RelatedIdeasStrip({ posts }: RelatedIdeasStripProps) {
  if (posts.length === 0) {
    return null;
  }

  return (
    <section className="space-y-4">
      <div className="flex items-end justify-between gap-4">
        <h2 className="font-display text-3xl text-charcoal">Related Ideas</h2>
        <Link href="/" className="text-sm font-medium text-dusk hover:text-dusk-dark">
          View feed
        </Link>
      </div>
      <div className="-mx-5 flex snap-x gap-4 overflow-x-auto px-5 pb-3">
        {posts.map((post) => (
          <Link
            key={post.slug}
            href={`/ideas/${post.slug}`}
            className="group w-64 shrink-0 snap-start overflow-hidden rounded-lg border border-charcoal/10 bg-white"
          >
            <Image
              src={post.thumbnailUrl}
              alt={post.title}
              width={560}
              height={420}
              sizes="256px"
              className="aspect-[4/3] w-full object-cover transition group-hover:scale-[1.02]"
            />
            <div className="p-3">
              <h3 className="text-sm font-semibold text-charcoal">{post.title}</h3>
              <p className="mt-1 line-clamp-2 text-xs leading-5 text-charcoal/60">
                {post.caption}
              </p>
            </div>
          </Link>
        ))}
      </div>
    </section>
  );
}
