import client from "./client";
import type { AuthRequest, AuthResponse } from "../types";

export async function login(payload: AuthRequest): Promise<AuthResponse> {
    const { data } = await client.post<AuthResponse>("/api/auth/login", payload);
    return data;
}
