import { notFound } from "next/navigation";
import { IdeaGrid } from "@/components/idea-grid";
import { MinimalNav } from "@/components/minimal-nav";
import { getCollections, getPostsByCollection } from "@/lib/mock-posts";

type CollectionDetailPageProps = {
  params: {
    slug: string;
  };
};

export function generateStaticParams() {
  return getCollections().map((collection) => ({ slug: collection.slug }));
}

export default function CollectionDetailPage({ params }: CollectionDetailPageProps) {
  const collection = getCollections().find((item) => item.slug === params.slug);
  const collectionPosts = getPostsByCollection(params.slug);

  if (!collection) {
    notFound();
  }

  return (
    <>
      <MinimalNav />
      <main className="mx-auto max-w-6xl px-5 py-12">
        <header className="mb-8 max-w-2xl">
          <p className="text-sm font-medium uppercase tracking-[0.16em] text-dusk">
            Collection
          </p>
          <h1 className="mt-3 font-display text-5xl text-charcoal">{collection.name}</h1>
          <p className="mt-3 leading-7 text-charcoal/68">
            {collection.count} {collection.count === 1 ? "idea" : "ideas"} gathered for this path.
          </p>
        </header>
        <IdeaGrid posts={collectionPosts} />
      </main>
    </>
  );
}
