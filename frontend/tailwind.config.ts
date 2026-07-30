import type { Config } from "tailwindcss";

// Visual language per PLANNING.md Section 7.1:
// near-neutral base + a single blue-hour dusk accent, used sparingly.
const config: Config = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        dusk: {
          DEFAULT: "#2b3a67",
          light: "#4a5d94",
          dark: "#1a2440",
        },
        paper: "#faf9f6",
        charcoal: "#242322",
      },
      fontFamily: {
        display: ["var(--font-display)"],
        body: ["var(--font-body)"],
      },
    },
  },
  plugins: [],
};
export default config;
