import client from "./client";
import type { TranslationInsertDTO, TranslationReadOnlyDTO } from "../types";

export async function createTranslation(payload: TranslationInsertDTO): Promise<TranslationReadOnlyDTO> {
    const { data } = await client.post<TranslationReadOnlyDTO>("/api/translations", payload);
    return data;
}

export async function getMyTranslations(): Promise<TranslationReadOnlyDTO[]> {
    const { data } = await client.get<TranslationReadOnlyDTO[]>("/api/translations");
    return data;
}
