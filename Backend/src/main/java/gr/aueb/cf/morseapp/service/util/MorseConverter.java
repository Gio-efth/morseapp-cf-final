package gr.aueb.cf.morseapp.service.util;

import java.util.HashMap;
import java.util.Map;

public class MorseConverter {

    private static final Map<Character, String> textToMorse = new HashMap<>();
    private static final Map<String, Character> morseToText = new HashMap<>();

    static {
        textToMorse.put('A', ".-");
        textToMorse.put('B', "-...");
        textToMorse.put('C', "-.-.");
        textToMorse.put('D', "-..");
        textToMorse.put('E', ".");
        textToMorse.put('F', "..-.");
        textToMorse.put('G', "--.");
        textToMorse.put('H', "....");
        textToMorse.put('I', "..");
        textToMorse.put('J', ".---");
        textToMorse.put('K', "-.-");
        textToMorse.put('L', ".-..");
        textToMorse.put('M', "--");
        textToMorse.put('N', "-.");
        textToMorse.put('O', "---");
        textToMorse.put('P', ".--.");
        textToMorse.put('Q', "--.-");
        textToMorse.put('R', ".-.");
        textToMorse.put('S', "...");
        textToMorse.put('T', "-");
        textToMorse.put('U', "..-");
        textToMorse.put('V', "...-");
        textToMorse.put('W', ".--");
        textToMorse.put('X', "-..-");
        textToMorse.put('Y', "-.--");
        textToMorse.put('Z', "--..");

        textToMorse.put('0', "-----");
        textToMorse.put('1', ".----");
        textToMorse.put('2', "..---");
        textToMorse.put('3', "...--");
        textToMorse.put('4', "....-");
        textToMorse.put('5', ".....");
        textToMorse.put('6', "-....");
        textToMorse.put('7', "--...");
        textToMorse.put('8', "---..");
        textToMorse.put('9', "----.");

        textToMorse.put(' ', "/");


        for (Map.Entry<Character, String> entry : textToMorse.entrySet()) {
            morseToText.put(entry.getValue(), entry.getKey());
        }
    }

    public static String toMorse(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : input.toUpperCase().toCharArray()) {
            String morse = textToMorse.get(character);
            if (morse != null) {
                stringBuilder.append(morse).append(" ");
            } else {
                stringBuilder.append("?");
            }
        }
        return stringBuilder.toString().trim();
    }

    public static String toText(String morseCode) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] morseWords = morseCode.trim().split(" ");

        for (String morseChar : morseWords) {
            Character letter = morseToText.get(morseChar);
            if (letter != null) {
                stringBuilder.append(letter);
            } else if (morseChar.equals("/")) {
                stringBuilder.append(" ");
            } else {
                stringBuilder.append("?");
            }
        }
        return stringBuilder.toString();
    }
}
