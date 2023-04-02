import java.util.HashMap;
import java.util.Map;

public class MorseCodeDecoder {
    public static String decodeBits(String incomeBytes) {

        incomeBytes = incomeBytes.substring(incomeBytes.indexOf("1"), incomeBytes.lastIndexOf("1") + 1);
        int temp1 = 0, max1Row = 0, temp0 = 0, max0Row = 0, period;

        for (char c : incomeBytes.toCharArray()) {
            if (c == '1') {
                if (max0Row < temp0) max0Row = temp0;
                temp1++;
                temp0 = 0;
            }
            if (c == '0') {
                if (max1Row < temp1) max1Row = temp1;
                temp0++;
                temp1 = 0;
            }
        }
        boolean isEdgeCase = !incomeBytes.replaceAll(new String(new char[max1Row]).replace('\0', '1'), "")
                                            .contains("1");

        period = max1Row >= 3 && !isEdgeCase ? max1Row / 3 : max1Row;

        if (!incomeBytes.contains("0")) return ".";
        if (max0Row < max1Row && isEdgeCase) period = max0Row;

        StringBuilder decodedMessage = new StringBuilder();
        String[] separatedWords = incomeBytes.split("0{" + period * 7 + "}");
        Map<String, String> morseCode = Map.of(new String(new char[period]).replace('\0', '1'), ".",
                                                new String(new char[period * 3]).replace('\0', '1'), "-");

        for (String w : separatedWords) {
            if (w.isEmpty()) continue;
            String[] separatedLetters = w.split("0{" + period * 3 + "}");
            for (String l : separatedLetters) {
                if (l.isEmpty()) continue;
                String[] separatedSymbols = l.split("0{" + period + "}");
                for (String s : separatedSymbols) {
                    if (s.isEmpty()) continue;
                    decodedMessage.append(morseCode.get(s.replaceAll("0", "")));
                }
                decodedMessage.append(" ");
            }
            decodedMessage.append("   ");
        }
        return decodedMessage.substring(0, decodedMessage.length() - 3).replaceAll("null", "");
    }

    public static String decodeMorse(String incomeCode) {

        String[] separatedWords = incomeCode.split(" {3}");
        StringBuilder decodedMessage = new StringBuilder();

        Map<String, String> morseCode = new HashMap<>();
        morseCode.put(".-", "a"); morseCode.put("-...", "b"); morseCode.put("-.-.", "c"); morseCode.put("-..", "d");
        morseCode.put(".", "e"); morseCode.put("..-.", "f"); morseCode.put("--.", "g"); morseCode.put("....", "h");
        morseCode.put("..", "i"); morseCode.put(".---", "j"); morseCode.put("-.-", "k"); morseCode.put(".-..", "l");
        morseCode.put("--", "m"); morseCode.put("-.", "n"); morseCode.put("---", "o"); morseCode.put(".--.", "p");
        morseCode.put("--.-", "q"); morseCode.put(".-.", "r"); morseCode.put("...", "s"); morseCode.put("-", "t");
        morseCode.put("..-", "u"); morseCode.put("...-", "v"); morseCode.put(".--", "w"); morseCode.put("-..-", "x");
        morseCode.put("-.--", "y"); morseCode.put("--..", "z"); morseCode.put("...---...", "SOS");
        morseCode.put("-.-.--", "!"); morseCode.put(".-.-.-", ".");

        for (String w : separatedWords) {
            String[] separatedLetters = w.split(" ");
            for (String l : separatedLetters) decodedMessage.append(morseCode.get(l));
            decodedMessage.append(" ");
        }
        return decodedMessage.substring(0, decodedMessage.length() - 1).toUpperCase().replaceAll("NULL", "");
    }
}