export type Direction = "TEXT_TO_MORSE" | "MORSE_TO_TEXT";

export interface AuthRequest {
    username: string;
    password: string;
}

export interface AuthResponse {
    token: string;
    username: string;
    role: string;
}

export interface TranslationInsertDTO {
    inputText: string;
    direction: Direction;
}

export interface TranslationReadOnlyDTO {
    id: number;
    inputText: string;
    outputText: string;
    direction: Direction;
    timestamp?: string;
}
