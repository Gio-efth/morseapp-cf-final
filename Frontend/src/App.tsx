import { useEffect, useState } from "react";
import LoginForm from "./components/LoginForm";
import TranslationsForm from "./components/TranslationsForm";
import TranslationsList from "./components/TranslationsList";
import type { AuthResponse } from "./types";

function App() {
    const [auth, setAuth] = useState<AuthResponse | null>(null);
    const [refreshKey, setRefreshKey] = useState(0);

    useEffect(() => {
        const raw = localStorage.getItem("auth");
        if (raw) {
            try {
                setAuth(JSON.parse(raw));
            } catch {
                localStorage.removeItem("auth");
            }
        }
    }, []);

    const onLoggedIn = (a: AuthResponse) => {
        setAuth(a);
        localStorage.setItem("auth", JSON.stringify(a));
        localStorage.setItem("jwt", a.token);
    };

    const logout = () => {
        setAuth(null);
        localStorage.removeItem("auth");
        localStorage.removeItem("jwt");
    };

    const onCreated = () => {
        setRefreshKey((k) => k + 1);
    };

    return (
        <div className="container">
            <h1>MorseApp</h1>

            {!auth ? (
                <LoginForm onLoggedIn={onLoggedIn} />
            ) : (
                <>
                    <div className="card user-area">
                        <div>
                            <strong>User:</strong> {auth.username} ({auth.role})
                        </div>
                        <button onClick={logout}>Logout</button>
                    </div>

                    <TranslationsForm onCreated={onCreated} />
                    <TranslationsList refreshKey={refreshKey} maxItems={10} />
                </>
            )}
        </div>
    );
}

export default App;
