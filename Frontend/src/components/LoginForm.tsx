import { useState, type FormEvent } from "react";
import { login } from "../api/auth";
import type { AuthResponse } from "../types";

type Props = { onLoggedIn: (auth: AuthResponse) => void };

export default function LoginForm({ onLoggedIn }: Props) {
    const [username, setUsername] = useState("");       // morseUser
    const [password, setPassword] = useState("");       // 12345
    const [loading, setLoading] = useState(false);
    const [err, setErr] = useState<string | null>(null);

    const submit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setErr(null);
        setLoading(true);
        try {
            const resp = await login({ username, password });
            localStorage.setItem("auth", JSON.stringify(resp));
            localStorage.setItem("jwt", resp.token);
            onLoggedIn(resp);
        } catch (error: unknown) {
            const message = error instanceof Error ? error.message : String(error);
            setErr(message || "Login error");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={submit} className="card">
            <h2 className="title-login">Login</h2>

            <div>
                <label className="login-input-title" htmlFor="username">Username</label>
                <input id="username" value={username} onChange={(e) => setUsername(e.target.value)} autoComplete="username" required />
            </div>

            <div>
                <label className="login-input-title" htmlFor="password">Password</label>
                <input id="password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} autoComplete="current-password" required />
            </div>

            {err && <div className="error">{err}</div>}

            <button type="submit" disabled={loading}>{loading ? "Signing in..." : "Sign in"}</button>
        </form>
    );
}
