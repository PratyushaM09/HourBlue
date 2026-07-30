const API_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

/**
 * Thin fetch wrapper (Section 10.2) - callers get a plain object back and
 * never have to deal with a thrown network error for a down/unreachable API
 * during early development.
 */
async function apiFetch<T>(path: string, init?: RequestInit): Promise<T | { status?: string }> {
  try {
    const res = await fetch(`${API_URL}${path}`, {
      ...init,
      headers: {
        "Content-Type": "application/json",
        ...init?.headers,
      },
      cache: "no-store",
    });

    if (!res.ok) {
      return { status: "error" };
    }

    return (await res.json()) as T;
  } catch {
    return { status: "unreachable" };
  }
}

export function getHealth() {
  return apiFetch<{ status: string; service: string }>("/api/health");
}
