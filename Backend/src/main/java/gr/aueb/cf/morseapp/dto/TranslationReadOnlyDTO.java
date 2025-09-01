package gr.aueb.cf.morseapp.dto;

import gr.aueb.cf.morseapp.core.enums.TranslationDirection;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TranslationReadOnlyDTO {

    private String inputText;
    private String outputText;
    private TranslationDirection direction;
    private LocalDateTime timestamp;
}