import { MinimalNav } from "@/components/minimal-nav";
import { TileGrid } from "@/components/tile-grid";
import { getMoods } from "@/lib/mock-posts";

export default function MoodsPage() {
  return (
    <>
      <MinimalNav />
      <main className="mx-auto max-w-6xl px-5 py-12">
        <header className="mb-8 max-w-2xl">
          <h1 className="font-display text-5xl text-charcoal">Moods</h1>
          <p className="mt-3 leading-7 text-charcoal/68">
            Associative paths through the mock Idea library.
          </p>
        </header>
        <TileGrid items={getMoods()} basePath="/moods" />
      </main>
    </>
  );
}
