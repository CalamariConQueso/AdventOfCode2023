package day1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalibrationParser {

    public void parse(List<String> calibrationLines) {
        int total = 0;
        for (String line : calibrationLines) {
            System.out.println("-------------------");
            System.out.println(line);

            String first = String.valueOf(getFirstDigit(line));
            String last = String.valueOf(getLastDigit(line));

            String number = first+last;
            System.out.println (number);
            total += Integer.parseInt(number);

        }
        System.out.println("Total: " + total);
    }

    private Integer getFirstDigit(String line) {
        // find first index of digit
        int firstDigitIndex = 999;
        int firstDigitChar = ' ';

        char[] lineChars = line.toCharArray();
        boolean breakFromFor = false;
        for (int i = 0; i < lineChars.length; i++) {
            if (breakFromFor) {
                break;
            }
            switch (lineChars[i]) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    firstDigitChar = lineChars[i];
                    firstDigitIndex = i;
                    System.out.println("First Digit: " + Character.getNumericValue(firstDigitChar) + ", index: " + firstDigitIndex);
                    breakFromFor = true;
                }
            }
        }

        // now find the first index of a digit string

        Map<Integer, Integer> numMap = new HashMap<Integer, Integer>();
        numMap.put(1, line.indexOf("one"));
        numMap.put(2, line.indexOf("two"));
        numMap.put(3, line.indexOf("three"));
        numMap.put(4, line.indexOf("four"));
        numMap.put(5, line.indexOf("five"));
        numMap.put(6, line.indexOf("six"));
        numMap.put(7, line.indexOf("seven"));
        numMap.put(8, line.indexOf("eight"));
        numMap.put(9, line.indexOf("nine"));

        numMap.values().removeIf(val -> val == -1);

        Map.Entry<Integer, Integer> min = null;
        for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }

        System.out.println("min.getValue(): " + (min == null ? "null" : min.getValue()));
        System.out.println("firstDigitIndex: " + firstDigitIndex + " (" + Character.getNumericValue(firstDigitChar) + ")");

        // if there were no spelled out digits, or if the first digit character has a smaller index than the
        // index of the first spelled out digit, return the first digit character.
        if (min == null || firstDigitIndex < min.getValue()) {

            return Character.getNumericValue(firstDigitChar);
        }

        return min.getKey();
    }

    private Integer getLastDigit(String line) {
        // find first index of digit
        int lastDigitIndex = 0;
        int lastDigitChar = ' ';

        char[] lineChars = line.toCharArray();
        boolean breakFromFor = false;
        for (int i = lineChars.length - 1; i >= 0; i--) {
            if (breakFromFor) {
                break;
            }
            switch (lineChars[i]) {
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    lastDigitChar = lineChars[i];
                    lastDigitIndex = i;
                    System.out.println("Last Digit: " + Character.getNumericValue(lastDigitChar) + ", index: " + lastDigitIndex);
                    breakFromFor = true;
                }
            }
        }

        // now find the first index of a digit string

        Map<Integer, Integer> numMap = new HashMap<Integer, Integer>();
        numMap.put(1, line.lastIndexOf("one"));
        numMap.put(2, line.lastIndexOf("two"));
        numMap.put(3, line.lastIndexOf("three"));
        numMap.put(4, line.lastIndexOf("four"));
        numMap.put(5, line.lastIndexOf("five"));
        numMap.put(6, line.lastIndexOf("six"));
        numMap.put(7, line.lastIndexOf("seven"));
        numMap.put(8, line.lastIndexOf("eight"));
        numMap.put(9, line.lastIndexOf("nine"));

        numMap.values().removeIf(val -> val == -1);

        Map.Entry<Integer, Integer> max = null;
        for (Map.Entry<Integer, Integer> entry : numMap.entrySet()) {
            if (max == null || max.getValue() < entry.getValue()) {
                max = entry;
            }
        }

        System.out.println("max.getValue(): " + (max == null ? "null" : max.getValue()));
        System.out.println("lastDigitIndex: " + lastDigitIndex + " (" + Character.getNumericValue(lastDigitChar) + ")");

        // if there were no spelled out digits, or if the last digit character has a bigger index than the
        // index of the last spelled out digit, return the last digit character.
        if (max == null || lastDigitIndex > max.getValue()) {

            return Character.getNumericValue(lastDigitChar);
        }

        return max.getKey();
    }

}
