import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MorseCodeDecoder {
    public static String decodeBitsAdvanced(String incomeBytes) {

        if (!incomeBytes.contains("1")) return "";

        if (incomeBytes.contains("0")) incomeBytes = cutEdgeZeroes(incomeBytes);
        if (!incomeBytes.contains("0")) return ".";

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

        if (incomeBytes.contains("010"))
            period = max1Row >= 3 && !isEdgeCase ? (int) ((max1Row * 0.65) / 3) : max1Row;
        else
            period = max1Row >= 3 && !isEdgeCase ? (((max1Row / 3) + (max0Row / 7)) / 2) : max1Row;

        if (max0Row < max1Row && isEdgeCase) period = max0Row;
        if (incomeBytes.contains("010") && period == 1) period = 2;

        StringBuilder decodedMessage = new StringBuilder();
        String[] separatedWords = incomeBytes.split("0{" + (int) (period * 7 * 0.85) + "}");

        for (int w = 0; w < separatedWords.length; w++) {
            if (separatedWords[w].isEmpty()) continue;
            separatedWords[w] = cutEdgeZeroes(separatedWords[w]);
            String[] separatedLetters = separatedWords[w].split("0{" + (int) (period * 3 * 0.9) + "}");
            for (int l = 0; l < separatedLetters.length; l++) {
                if (separatedLetters[l].isEmpty()) continue;
                separatedLetters[l] = cutEdgeZeroes(separatedLetters[l]);
                String[] separatedSymbols = separatedLetters[l].split("0");
                for (int s = 0; s < separatedSymbols.length; s++) {
                    if (separatedSymbols[s].isEmpty()) continue;
                    separatedSymbols[s] = cutEdgeZeroes(separatedSymbols[s]);
                    if (separatedSymbols[s].length() < period * 2.4)
                        decodedMessage.append(".");
                    else decodedMessage.append("-");
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
        morseCode.put("-.--", "y"); morseCode.put("--..", "z"); morseCode.put("...---...", "SOS"); morseCode.put("-.-.--", "!");
        morseCode.put(".-.-.-", "."); morseCode.put(".----", "1"); morseCode.put("..---", "2"); morseCode.put("...--", "3");
        morseCode.put("....-", "4"); morseCode.put(".....", "5"); morseCode.put("-....", "6"); morseCode.put("--...", "7");
        morseCode.put("---..", "8"); morseCode.put("----.", "9"); morseCode.put("-----", "0");

        for (String w : separatedWords) {
            String[] separatedLetters = w.split(" ");
            for (String l : separatedLetters) decodedMessage.append(morseCode.get(l));
            decodedMessage.append(" ");
        }
        return decodedMessage.substring(0, decodedMessage.length() - 1).toUpperCase().replaceAll("NULL", "");
    }

    private static String cutEdgeZeroes(String incomeBytes) {
        incomeBytes = incomeBytes.substring(incomeBytes.indexOf("1"), incomeBytes.lastIndexOf("1") + 1);
        return incomeBytes;
    }
}