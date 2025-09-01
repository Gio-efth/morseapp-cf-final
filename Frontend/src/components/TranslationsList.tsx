import { useEffect, useState } from "react";
import { getMyTranslations } from "../api/translations";
import type { TranslationReadOnlyDTO } from "../types";

type Props = {
    refreshKey: number;
    maxItems?: number;
};

export default function TranslationsList({ refreshKey, maxItems }: Props) {
    const [items, setItems] = useState<TranslationReadOnlyDTO[]>([]);
    const [loading, setLoading] = useState(false);
    const [err, setErr] = useState<string | null>(null);
    const ms = (ts?: string) => (ts ? Date.parse(ts) : 0) || 0;

    useEffect(() => {
        let cancelled = false;

        (async () => {
            setLoading(true);
            setErr(null);
            try {
                const data = await getMyTranslations();

                const sorted = data
                    .slice()
                    .sort((a, b) => {
                        const d = ms(b.timestamp) - ms(a.timestamp); // desc
                        return d !== 0 ? d : (b.id ?? 0) - (a.id ?? 0); // 2ο κριτήριο
                    });

                const out =
                    typeof maxItems === "number" && maxItems > 0
                        ? sorted.slice(0, maxItems)
                        : sorted;

                if (!cancelled) setItems(out);
            } catch (e: unknown) {
                if (!cancelled) {
                    const msg = e instanceof Error ? e.message : String(e);
                    setErr(msg || "Fetch failed");
                }
            } finally {
                if (!cancelled) setLoading(false);
            }
        })();

        return () => {
            cancelled = true;
        };
    }, [refreshKey, maxItems]);

    const fmt = (ts?: string) => (ts ? new Date(ts).toLocaleString() : "");

    return (
        <div className="card">
            <h3>My Translations</h3>
            {loading && <div>Loading…</div>}
            {err && <div className="error">{err}</div>}
            <ul className="list">
                {items.map((t) => (
                    <li key={t.id}>
                        <div><strong>{t.direction}</strong> — {t.inputText}</div>
                        <div>{t.outputText}</div>
                        <small>{fmt(t.timestamp)}</small>
                    </li>
                ))}
            </ul>
        </div>
    );
}
