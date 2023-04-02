public class PigLatin {
    public static String pigIt(String incomeString) {
        String[] separatedWords = incomeString.split(" ");

        for (int i = 0; i < separatedWords.length; i++) {
            String[] separatedLetters = separatedWords[i].split("");
            if (!Character.isAlphabetic(separatedLetters[0].charAt(0))) continue;
            separatedWords[i] = shiftArrayCell(separatedLetters) + "ay";
        }
        return String.join(" ", separatedWords);
    }

    public static String shiftArrayCell(String[] string) {
        String temp = string[0];
        System.arraycopy(string, 1, string, 0, string.length - 1);
        string[string.length-1] = temp;
        return String.join("", string);
    }
}