import Link from "next/link";

const links = [
  { href: "/", label: "Ideas" },
  { href: "/collections", label: "Collections" },
  { href: "/moods", label: "Moods" },
];

export function MinimalNav() {
  return (
    <header className="sticky top-0 z-30 border-b border-charcoal/10 bg-paper/90 backdrop-blur">
      <nav className="mx-auto flex max-w-6xl items-center justify-between px-5 py-4">
        <Link href="/" className="font-display text-2xl text-charcoal">
          HourBlue
        </Link>
        <div className="flex items-center gap-1 rounded-full border border-charcoal/10 bg-white/70 p-1 text-sm">
          {links.map((link) => (
            <Link
              key={link.href}
              href={link.href}
              className="rounded-full px-3 py-2 text-charcoal/70 transition hover:bg-charcoal hover:text-paper"
            >
              {link.label}
            </Link>
          ))}
        </div>
      </nav>
    </header>
  );
}
