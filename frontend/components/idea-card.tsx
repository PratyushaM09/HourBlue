import Link from "next/link";
import Image from "next/image";
import type { Post } from "@/lib/mock-posts";

type IdeaCardProps = {
  post: Post;
  priority?: boolean;
};

export function IdeaCard({ post, priority = false }: IdeaCardProps) {
  return (
    <article className="mb-5 break-inside-avoid overflow-hidden rounded-lg border border-charcoal/10 bg-white shadow-sm transition hover:-translate-y-0.5 hover:shadow-md">
      <Link href={`/ideas/${post.slug}`} className="block">
        <Image
          src={post.thumbnailUrl}
          alt={post.title}
          width={700}
          height={875}
          priority={priority}
          sizes="(min-width: 1024px) 33vw, (min-width: 640px) 50vw, 100vw"
          className="aspect-[4/5] w-full object-cover"
        />
        <div className="space-y-3 p-4">
          <div>
            <h2 className="text-base font-semibold leading-snug text-charcoal">
              {post.title}
            </h2>
            <p className="mt-1 text-sm leading-6 text-charcoal/65">{post.caption}</p>
          </div>
          <div className="flex flex-wrap gap-2 text-xs text-charcoal/60">
            {post.moods.slice(0, 2).map((mood) => (
              <span key={mood} className="rounded-full bg-[#e8f0dc] px-2.5 py-1">
                {mood}
              </span>
            ))}
            {post.collections.slice(0, 1).map((collection) => (
              <span key={collection} className="rounded-full bg-[#f5e7d2] px-2.5 py-1">
                {collection}
              </span>
            ))}
          </div>
        </div>
      </Link>
    </article>
  );
}
