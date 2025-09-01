package gr.aueb.cf.morseapp.rest;

import gr.aueb.cf.morseapp.dto.TranslationInsertDTO;
import gr.aueb.cf.morseapp.dto.TranslationReadOnlyDTO;
import gr.aueb.cf.morseapp.model.User;
import gr.aueb.cf.morseapp.service.TranslationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "translations", description = "Μετατροπές Morse ↔ Κείμενο")
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<TranslationReadOnlyDTO> createTranslation(
            @RequestBody @Valid TranslationInsertDTO dto,
            @AuthenticationPrincipal User user) {
        TranslationReadOnlyDTO response = translationService.createTranslation(dto, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TranslationReadOnlyDTO>> getUserTranslations(
            @AuthenticationPrincipal User user) {
        List<TranslationReadOnlyDTO> translations = translationService.getTranslationsByUser(user);
        return ResponseEntity.ok(translations);
    }
}
