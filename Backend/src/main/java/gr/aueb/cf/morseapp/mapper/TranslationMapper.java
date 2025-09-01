package gr.aueb.cf.morseapp.mapper;

import gr.aueb.cf.morseapp.dto.TranslationInsertDTO;
import gr.aueb.cf.morseapp.dto.TranslationReadOnlyDTO;
import gr.aueb.cf.morseapp.model.Translation;
import gr.aueb.cf.morseapp.model.User;

import java.time.LocalDateTime;

public class TranslationMapper {

    public static Translation toEntity(TranslationInsertDTO dto, String outputText, User user) {
        return Translation.builder()
                .inputText(dto.getInputText())
                .outputText(outputText)
                .direction(dto.getDirection())
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();
    }

    public static TranslationReadOnlyDTO toReadOnlyDTO(Translation translation) {
        return TranslationReadOnlyDTO.builder()
                .inputText(translation.getInputText())
                .outputText(translation.getOutputText())
                .direction(translation.getDirection())
                .timestamp(translation.getTimestamp())
                .build();
    }
}
