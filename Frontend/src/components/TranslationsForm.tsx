import { useState , useEffect} from "react";
import { createTranslation } from "../api/translations";
import type { Direction, TranslationReadOnlyDTO } from "../types";

type Props = {
    onCreated: (t: TranslationReadOnlyDTO) => void;
};

export default function TranslationsForm({ onCreated }: Props) {
    const [inputText, setInputText] = useState("");
    const [direction, setDirection] = useState<Direction>("TEXT_TO_MORSE");
    const [busy, setBusy] = useState(false);
    const [err, setErr] = useState<string | null>(null);

    useEffect(() => {
        if (direction === "MORSE_TO_TEXT") {
            document.title = "-- --- .-. ... . .- .--. .--.";
        } else {
            document.title = "MorseApp";
        }
    }, [direction]);

    const submit = async (e: React.FormEvent) => {
        e.preventDefault();
        setErr(null);
        setBusy(true);
        try {
            const created = await createTranslation({ inputText, direction });
            onCreated(created);
            setInputText("");
        } catch (error: unknown) {
            const message = error instanceof Error ? error.message : String(error);
            setErr(message || "Create failed");
        } finally {
            setBusy(false);
        }
    };

    return (
        <form onSubmit={submit} className="card">
            <h3>Create Translation</h3>

            <textarea value={inputText} onChange={(e) => setInputText(e.target.value)} required />

            <div className="direction-buttons">
                <button
                type="button"
                className={direction === "TEXT_TO_MORSE" ? "direction-buttons active" : "direction-buttons inactive"}
                onClick={() => setDirection("TEXT_TO_MORSE")}
                >
                    Text to Morse
                </button>

                <button
                    type="button"
                    className={direction === "MORSE_TO_TEXT" ? "direction-buttons active" : "direction-buttons inactive"}
                    onClick={() => setDirection("MORSE_TO_TEXT")}
                >
                    Morse to Text
                </button>
            </div>

            {err && <div className="error">{err}</div>}

            <button disabled={busy}>{busy ? "Translating..." : "Translate"}</button>
        </form>
    );
}
