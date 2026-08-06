import type { Post } from "@/lib/mock-posts";
import { IdeaCard } from "@/components/idea-card";

type IdeaGridProps = {
  posts: Post[];
};

export function IdeaGrid({ posts }: IdeaGridProps) {
  return (
    <div className="columns-1 gap-5 sm:columns-2 lg:columns-3">
      {posts.map((post, index) => (
        <IdeaCard key={post.slug} post={post} priority={index < 3} />
      ))}
    </div>
  );
}
