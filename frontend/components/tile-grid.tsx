import Link from "next/link";
import Image from "next/image";

type Tile = {
  name: string;
  slug: string;
  coverImageUrl: string;
  count: number;
};

type TileGridProps = {
  items: Tile[];
  basePath: "/collections" | "/moods";
};

export function TileGrid({ items, basePath }: TileGridProps) {
  return (
    <div className="grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
      {items.map((item) => (
        <Link
          key={item.slug}
          href={`${basePath}/${item.slug}`}
          className="group overflow-hidden rounded-lg border border-charcoal/10 bg-white"
        >
          <div className="relative aspect-[5/4]">
            <Image
              src={item.coverImageUrl}
              alt={item.name}
              fill
              sizes="(min-width: 1024px) 33vw, (min-width: 640px) 50vw, 100vw"
              className="object-cover transition group-hover:scale-[1.02]"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-charcoal/70 via-charcoal/10 to-transparent" />
            <div className="absolute bottom-4 left-4 right-4 text-paper">
              <h2 className="font-display text-3xl leading-tight">{item.name}</h2>
              <p className="mt-1 text-sm text-paper/80">
                {item.count} {item.count === 1 ? "idea" : "ideas"}
              </p>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}
