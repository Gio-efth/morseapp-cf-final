package gr.aueb.cf.morseapp.service;

import gr.aueb.cf.morseapp.core.enums.TranslationDirection;
import gr.aueb.cf.morseapp.dto.TranslationInsertDTO;
import gr.aueb.cf.morseapp.dto.TranslationReadOnlyDTO;
import gr.aueb.cf.morseapp.mapper.TranslationMapper;
import gr.aueb.cf.morseapp.model.Translation;
import gr.aueb.cf.morseapp.model.User;
import gr.aueb.cf.morseapp.repository.TranslationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TranslationServiceTest {

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationService translationService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTranslation_textToMorse_savesAndReturnsDTO() {
        User user = User.builder().id(1L).username("morseUser").build();
        TranslationInsertDTO dto = TranslationInsertDTO.builder()
                .inputText("SOS HELP")
                .direction(TranslationDirection.TEXT_TO_MORSE)
                .build();

        Translation toSave = Translation.builder()
                .inputText("SOS HELP")
                .outputText("... --- ... / .... . .-.. .--.")
                .direction(TranslationDirection.TEXT_TO_MORSE)
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();

        when(translationRepository.save(any(Translation.class))).thenReturn(toSave);

        TranslationReadOnlyDTO result = translationService.createTranslation(dto, user);

        assertEquals("SOS HELP", result.getInputText());
        assertEquals("... --- ... / .... . .-.. .--.", result.getOutputText());
        assertEquals(TranslationDirection.TEXT_TO_MORSE, result.getDirection());
        assertNotNull(result.getTimestamp());

        verify(translationRepository, times(1)).save(any(Translation.class));
    }

    @Test
    void getTranslationsByUser_returnsList() {
        User user = User.builder().id(1L).username("morseUser").build();

        Translation one = Translation.builder()
                .inputText("SOS")
                .outputText("... --- ...")
                .direction(TranslationDirection.TEXT_TO_MORSE)
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();

        when(translationRepository.findByUser(user)).thenReturn(List.of(one));

        var result = translationService.getTranslationsByUser(user);

        assertEquals(1, result.size());
        assertEquals("SOS", result.get(0).getInputText());
    }
}
