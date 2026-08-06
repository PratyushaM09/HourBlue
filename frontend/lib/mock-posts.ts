export type ContentType = "IMAGE" | "ARTICLE" | "VIDEO" | "PRODUCT" | "OTHER";

export type Post = {
  slug: string;
  title: string;
  contentType: ContentType;
  imageUrl: string;
  thumbnailUrl: string;
  mediumUrl: string;
  caption: string;
  tags: string;
  place: string;
  capturedDate: string;
  capturedTime?: string;
  weather?: string;
  featured: boolean;
  pinterestUrl?: string;
  externalUrl?: string;
  affiliateUrl?: string;
  collections: string[];
  moods: string[];
};

export type CollectionSummary = {
  name: string;
  slug: string;
  coverImageUrl: string;
  count: number;
};

export type MoodSummary = {
  name: string;
  slug: string;
  coverImageUrl: string;
  count: number;
};

export const posts: Post[] = [
  {
    slug: "blue-hour-balcony-garden",
    title: "Blue Hour Balcony Garden",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1000&q=82",
    caption:
      "A small green corner held onto the last cool light of the day.",
    tags: "balcony, garden, dusk, calm",
    place: "Jaipur",
    capturedDate: "2026-08-01",
    capturedTime: "18:34",
    weather: "CLOUDY",
    featured: true,
    pinterestUrl: "https://www.pinterest.com/",
    externalUrl: "https://unsplash.com/",
    collections: ["Gardens", "Evenings"],
    moods: ["Peaceful", "Quiet"],
  },
  {
    slug: "rain-window-worktable",
    title: "Rain Window Worktable",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1494783367193-149034c05e8f?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1494783367193-149034c05e8f?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1494783367193-149034c05e8f?auto=format&fit=crop&w=1000&q=82",
    caption:
      "A rainy desk mood for slow projects, open tabs, and one warm light.",
    tags: "rain, desk, window, worktable",
    place: "Bengaluru",
    capturedDate: "2026-07-19",
    capturedTime: "16:10",
    weather: "RAINY",
    featured: false,
    externalUrl: "https://unsplash.com/",
    collections: ["Rainy Days", "Corners"],
    moods: ["Rainy", "Quiet"],
  },
  {
    slug: "golden-market-lane",
    title: "Golden Market Lane",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?auto=format&fit=crop&w=1000&q=82",
    caption:
      "Late light on old stone, bright fabric, and the kind of lane that keeps pulling you forward.",
    tags: "market, lane, golden, travel",
    place: "Jaipur",
    capturedDate: "2026-08-01",
    capturedTime: "17:42",
    weather: "SUNNY",
    featured: true,
    pinterestUrl: "https://www.pinterest.com/",
    affiliateUrl: "https://example.com/shop",
    collections: ["City Walks", "Evenings"],
    moods: ["Golden", "Adventure"],
  },
  {
    slug: "quiet-moon-over-rooftops",
    title: "Quiet Moon Over Rooftops",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1505506874110-6a7a69069a08?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1505506874110-6a7a69069a08?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1505506874110-6a7a69069a08?auto=format&fit=crop&w=1000&q=82",
    caption:
      "A rooftop pause with just enough sky to make everything else feel smaller.",
    tags: "moon, rooftop, night, sky",
    place: "Delhi",
    capturedDate: "2026-06-11",
    capturedTime: "21:02",
    weather: "CLOUDY",
    featured: false,
    collections: ["Skies", "Evenings"],
    moods: ["Peaceful", "Quiet"],
  },
  {
    slug: "diy-paper-lantern-table",
    title: "Paper Lantern Table",
    contentType: "ARTICLE",
    imageUrl:
      "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1513519245088-0e12902e5a38?auto=format&fit=crop&w=1000&q=82",
    caption:
      "An easy table setup idea built around paper, texture, and one soft glow.",
    tags: "diy, lantern, table, decor",
    place: "Home",
    capturedDate: "2026-05-24",
    weather: "SUNNY",
    featured: true,
    externalUrl: "https://example.com/tutorial",
    affiliateUrl: "https://example.com/materials",
    collections: ["DIY", "Corners"],
    moods: ["Joyful", "Golden"],
  },
  {
    slug: "forest-path-after-rain",
    title: "Forest Path After Rain",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1448375240586-882707db888b?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1448375240586-882707db888b?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1448375240586-882707db888b?auto=format&fit=crop&w=1000&q=82",
    caption:
      "Wet leaves, clean air, and a path that looks better when it does not explain itself.",
    tags: "forest, rain, path, green",
    place: "Coorg",
    capturedDate: "2026-07-22",
    capturedTime: "09:18",
    weather: "RAINY",
    featured: false,
    collections: ["Nature", "Rainy Days"],
    moods: ["Rainy", "Adventure"],
  },
  {
    slug: "cafe-corner-with-blue-cup",
    title: "Cafe Corner With Blue Cup",
    contentType: "PRODUCT",
    imageUrl:
      "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=1000&q=82",
    caption:
      "A coffee corner built from small useful things: a good cup, a clean table, a quiet hour.",
    tags: "cafe, coffee, table, blue",
    place: "Mumbai",
    capturedDate: "2026-04-08",
    capturedTime: "10:05",
    weather: "SUNNY",
    featured: false,
    affiliateUrl: "https://example.com/blue-cup",
    collections: ["Cafes", "Corners"],
    moods: ["Peaceful", "Joyful"],
  },
  {
    slug: "wildflower-field-note",
    title: "Wildflower Field Note",
    contentType: "IMAGE",
    imageUrl:
      "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=1400&q=85",
    thumbnailUrl:
      "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=700&q=80",
    mediumUrl:
      "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=1000&q=82",
    caption:
      "A bright little field note for days that need color without noise.",
    tags: "wildflowers, mountains, field, bright",
    place: "Himachal",
    capturedDate: "2026-06-03",
    capturedTime: "11:22",
    weather: "SUNNY",
    featured: true,
    collections: ["Nature", "Skies"],
    moods: ["Joyful", "Adventure"],
  },
];

export const featuredPost = posts.find((post) => post.featured) ?? posts[0];

export function getPostBySlug(slug: string) {
  return posts.find((post) => post.slug === slug);
}

export function getRelatedPosts(post: Post, limit = 6) {
  return posts
    .filter((candidate) => candidate.slug !== post.slug)
    .map((candidate) => ({
      post: candidate,
      score:
        sharedCount(candidate.collections, post.collections) * 3 +
        sharedCount(candidate.moods, post.moods) * 2 +
        (candidate.place === post.place ? 2 : 0),
    }))
    .filter((candidate) => candidate.score > 0)
    .sort((a, b) => b.score - a.score)
    .slice(0, limit)
    .map((candidate) => candidate.post);
}

export function getCollections(): CollectionSummary[] {
  return summarizeBy("collections");
}

export function getMoods(): MoodSummary[] {
  return summarizeBy("moods");
}

export function getPostsByCollection(slug: string) {
  return posts.filter((post) =>
    post.collections.some((collection) => toSlug(collection) === slug),
  );
}

export function getPostsByMood(slug: string) {
  return posts.filter((post) => post.moods.some((mood) => toSlug(mood) === slug));
}

export function toSlug(value: string) {
  return value.toLowerCase().replace(/[^a-z0-9]+/g, "-").replace(/(^-|-$)/g, "");
}

function summarizeBy(key: "collections" | "moods") {
  const summaries = new Map<string, CollectionSummary | MoodSummary>();

  for (const post of posts) {
    for (const name of post[key]) {
      const slug = toSlug(name);
      const existing = summaries.get(slug);
      summaries.set(slug, {
        name,
        slug,
        coverImageUrl: existing?.coverImageUrl ?? post.mediumUrl,
        count: (existing?.count ?? 0) + 1,
      });
    }
  }

  return Array.from(summaries.values()).sort((a, b) => a.name.localeCompare(b.name));
}

function sharedCount(a: string[], b: string[]) {
  return a.filter((value) => b.includes(value)).length;
}
