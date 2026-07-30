import { getHealth } from "@/lib/api";

// Milestone 1 placeholder Home page - just proves the frontend can reach the
// backend end to end. Real Home (Section 3.1 / 7.2) comes in Milestone 3.
export default async function HomePage() {
  const health = await getHealth();

  return (
    <main className="min-h-screen flex flex-col items-center justify-center px-6 text-center">
      <h1 className="font-display text-5xl md:text-6xl text-dusk mb-4">
        HourBlue
      </h1>
      <p className="text-charcoal/70 max-w-md mb-8">
        A personal visual journal. What happens after someone falls in love
        with one photo?
      </p>
      <div className="text-sm font-mono px-4 py-2 rounded bg-dusk/5 text-dusk">
        backend status: {health.status ?? "unreachable"}
      </div>
    </main>
  );
}
