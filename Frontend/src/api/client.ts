import axios, {
    AxiosHeaders,
    type InternalAxiosRequestConfig,
    type RawAxiosRequestHeaders,
} from "axios";

const baseURL = (import.meta.env.VITE_API_BASE ?? "http://localhost:8080") as string;

const client = axios.create({ baseURL });

client.interceptors.request.use((config: InternalAxiosRequestConfig) => {
    let token: string | null = null;

    const raw = localStorage.getItem("auth");
    if (raw) {
        try {
            const parsed = JSON.parse(raw) as { token?: string };
            if (parsed && typeof parsed.token === "string") {
                token = parsed.token;
            }
        } catch {
            token = null;
        }
    }
    if (!token) {
        token = localStorage.getItem("jwt");
    }

    if (token) {
        if (!config.headers) {
            config.headers = new AxiosHeaders();
        }

        if (config.headers instanceof AxiosHeaders) {
            config.headers.set("Authorization", `Bearer ${token}`);
        } else {
            (config.headers as RawAxiosRequestHeaders)["Authorization"] = `Bearer ${token}`;
        }
    }

    return config;
});

export default client;