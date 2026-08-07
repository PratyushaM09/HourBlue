import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "HourBlue",
  description: "A visual discovery site for quiet ideas, moods, and useful inspiration.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="font-body">{children}</body>
    </html>
  );
}
