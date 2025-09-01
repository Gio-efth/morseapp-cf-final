package gr.aueb.cf.morseapp.service;

import gr.aueb.cf.morseapp.core.enums.TranslationDirection;
import gr.aueb.cf.morseapp.dto.TranslationInsertDTO;
import gr.aueb.cf.morseapp.dto.TranslationReadOnlyDTO;
import gr.aueb.cf.morseapp.mapper.TranslationMapper;
import gr.aueb.cf.morseapp.model.Translation;
import gr.aueb.cf.morseapp.model.User;
import gr.aueb.cf.morseapp.repository.TranslationRepository;
import gr.aueb.cf.morseapp.service.util.MorseConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TranslationService {

    private final TranslationRepository translationRepository;

    public TranslationReadOnlyDTO createTranslation(TranslationInsertDTO dto, User user) {
        String output = convert(dto.getInputText(), dto.getDirection());
        Translation translation = TranslationMapper.toEntity(dto, output, user);
        Translation saved = translationRepository.save(translation);
        return TranslationMapper.toReadOnlyDTO(saved);
    }

    public List<TranslationReadOnlyDTO> getTranslationsByUser(User user) {
        return translationRepository.findByUser(user)
                .stream()
                .map(TranslationMapper::toReadOnlyDTO)
                .collect(Collectors.toList());
    }

    private String convert(String input, TranslationDirection direction) {
        return switch (direction) {
            case TEXT_TO_MORSE -> MorseConverter.toMorse(input);
            case MORSE_TO_TEXT -> MorseConverter.toText(input);
        };
    }
}
