import { notFound } from "next/navigation";
import { IdeaGrid } from "@/components/idea-grid";
import { MinimalNav } from "@/components/minimal-nav";
import { getMoods, getPostsByMood } from "@/lib/mock-posts";

type MoodDetailPageProps = {
  params: {
    slug: string;
  };
};

export function generateStaticParams() {
  return getMoods().map((mood) => ({ slug: mood.slug }));
}

export default function MoodDetailPage({ params }: MoodDetailPageProps) {
  const mood = getMoods().find((item) => item.slug === params.slug);
  const moodPosts = getPostsByMood(params.slug);

  if (!mood) {
    notFound();
  }

  return (
    <>
      <MinimalNav />
      <main className="mx-auto max-w-6xl px-5 py-12">
        <header className="mb-8 max-w-2xl">
          <p className="text-sm font-medium uppercase tracking-[0.16em] text-dusk">
            Mood
          </p>
          <h1 className="mt-3 font-display text-5xl text-charcoal">{mood.name}</h1>
          <p className="mt-3 leading-7 text-charcoal/68">
            {mood.count} {mood.count === 1 ? "idea" : "ideas"} with this feeling.
          </p>
        </header>
        <IdeaGrid posts={moodPosts} />
      </main>
    </>
  );
}
