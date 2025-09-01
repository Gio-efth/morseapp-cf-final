package gr.aueb.cf.morseapp.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MorseConverterTest {

    @Test
    void textToMorse_basic() {
        String input = "HELLO";
        String morse = MorseConverter.toMorse(input);
        assertEquals(".... . .-.. .-.. ---", morse);
    }

    @Test
    void morseToText_basic() {
        String input = ".... . .-.. .-.. ---";
        String text = MorseConverter.toText(input);
        assertEquals("HELLO", text);
    }

    @Test
    void textToMorse_unknownChars() {
        String input = "ΓΕΙΑ ΣΟΥ!"; // τα ελληνικά θα βγουν '?'
        String morse = MorseConverter.toMorse(input);
        assertTrue(morse.contains("?"));
    }

    @Test
    void morseToText_slashAsSpace() {
        String input = ".... . .-.. .-.. --- / -.-. --- -.. .. -. --. / ..-. .- -.-. - --- .-. -.--";
        String text = MorseConverter.toText(input);
        assertEquals("HELLO CODING FACTORY", text);
    }
}
