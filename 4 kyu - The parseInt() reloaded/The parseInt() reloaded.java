import java.util.*;

public class Parser {
    public static int parseInt(String incomeString) {

        final Map<String, Integer> singles = new HashMap<>();
        singles.put("one", 1); singles.put("two", 2); singles.put("three", 3); singles.put("four", 4);
        singles.put("five", 5); singles.put("six", 6); singles.put("seven", 7); singles.put("eight", 8);
        singles.put("nine", 9); singles.put("zero", 0); singles.put("ten", 10);

        final Map<String, Integer> secondTen = new HashMap<>();
        secondTen.put("eleven", 11); secondTen.put("twelve", 12); secondTen.put("thirteen", 13);
        secondTen.put("fourteen", 14); secondTen.put("fifteen", 15); secondTen.put("sixteen", 16);
        secondTen.put("seventeen", 17); secondTen.put("eighteen", 18); secondTen.put("nineteen", 19);

        final Map<String, Integer> tens = new HashMap<>();
        tens.put("twenty", 20); tens.put("thirty", 30); tens.put("forty", 40); tens.put("fifty", 50);
        tens.put("sixty", 60); tens.put("seventy", 70); tens.put("eighty", 80); tens.put("ninety", 90);

        incomeString = incomeString.replace(" and ", " ");
        incomeString = incomeString.replace("-", " ");
        
        if (incomeString.contains("million")) return 1_000_000;

        if (incomeString.contains(" thousand ")) {
            String[] separate = incomeString.split(" thousand ");
            String[] thousands = separate[0].split(" ");
            String[] units = separate[1].split(" ");
            return (radixProcessing(singles, secondTen, tens, thousands) * 1000
                    + radixProcessing(singles, secondTen, tens, units));
        } else if (incomeString.contains("thousand")) {
            String[] separate = incomeString.split(" ");
            return radixProcessing(singles, secondTen, tens, separate) * 1000;
        } else {
            String[] separate = incomeString.split(" ");
            return radixProcessing(singles, secondTen, tens, separate);
        }
    }

    private static int radixProcessing(Map<String, Integer> singles,
                                       Map<String, Integer> secondTen,
                                       Map<String, Integer> tens,
                                       String[] separateNumbers) {

        List<Integer> temp = new ArrayList<>();
        int result = 0;

        for (String separateNumber : separateNumbers) {
            if (tens.containsKey(separateNumber)) {
                result += tens.get(separateNumber);
                continue;
            }
            if (secondTen.containsKey(separateNumber)) {
                result += secondTen.get(separateNumber);
                continue;
            }
            if (singles.containsKey(separateNumber)) {
                result += singles.get(separateNumber);
                continue;
            }
            if (Objects.equals(separateNumber, "hundred")) {
                temp.add(result * 100);
                result = 0;
            }
        }
        temp.add(result);
        return temp.stream().mapToInt(Integer::valueOf).sum();
    }
}