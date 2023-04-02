public class Scramblies {
    
    public static boolean scramble(String characterSet, String word) {

        if (characterSet.length() < word.length()) return false;
        if (characterSet.contains(word)) return true;

        String incomeSet = characterSet.replaceAll("[-+.^:,]1234567890","").toLowerCase();
        String[] keyWord = word.split("");

        boolean canBeScrambled = true;

        for (String string : keyWord) {
            if (!incomeSet.contains(string)) {
                canBeScrambled = false;
                break;
            }
            incomeSet = incomeSet.replaceFirst(string, "0");
        }
        return canBeScrambled;
    }
}