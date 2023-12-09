package day9;

import java.util.ArrayList;
import java.util.List;

public class OasisParser {

    List<Long> extrapolations = new ArrayList<Long>();

    public void parse(List<String> readings) {
        Long total = 0L;
        Long totalFirst = 0L;
        for (String line : readings) {
            List<Long> readingList = new ArrayList<>();
            String[] lineArray = line.split("\\s+");
            for (String num : lineArray) {
                readingList.add(Long.parseLong(num));
            }
            
            // System.out.println(readingList);
            
            Container c = extrapolateLast(readingList, null, false);
            total += (c.value + readingList.get(readingList.size() - 1));
//            System.out.println("Next Value: " + (c.value + readingList.get(readingList.size() - 1)));


            c = extrapolateFirst(new Container(readingList, null, false));
            totalFirst += (readingList.get(0) - c.value);
//            System.out.println("Next Value: " + (readingList.get(0) - c.value));

        }
        System.out.println("Total: " + total);
        System.out.println("Total First: " + totalFirst);
    }

    private Container extrapolateLast(List<Long> inputList, Long extrapolatedValue, Boolean complete) {
        List<Long> newList = new ArrayList<>();

        if (complete) {
            return new Container(null, extrapolatedValue, true);
        }

        for(int i = 1; i < inputList.size(); i++) {
            newList.add(inputList.get(i) - inputList.get(i - 1));
        }

        if (extrapolatedValue == null) {
            extrapolatedValue = newList.get(newList.size() -1);
//            System.out.println("Starting with: " + extrapolatedValue);
        } else {
//            System.out.println("Adding " + newList.get(newList.size() -1) + " to " + extrapolatedValue);
            extrapolatedValue += newList.get(newList.size() -1);
        }

        if (allElementsZero(newList)) {
            return new Container(null, extrapolatedValue, true);
        }

        // System.out.println(extrapolatedValue);
        Container c = extrapolateLast(newList, extrapolatedValue, false);

        return c;
    }

    private Container extrapolateFirst(Container inputContainer) {
        List<Long> newList = new ArrayList<>();

//        System.out.println(inputContainer.longList);
        for(int i = 1; i < inputContainer.longList.size(); i++) {
            newList.add(inputContainer.longList.get(i) - inputContainer.longList.get(i - 1));
        }

        Container c = new Container(newList, null, allElementsZero(newList));
        while (!c.complete) {
            c = extrapolateFirst(c);
        }

        // found all zeroes, so work from the bottom up

        Long extrapolatedValue = c.value;

        if (extrapolatedValue == null) {
            extrapolatedValue = newList.get(0);
//            System.out.println("Starting with: " + extrapolatedValue);
        } else {
//            System.out.println("Subtracting " + extrapolatedValue + " from " + newList.get(0));
            extrapolatedValue = newList.get(0) - extrapolatedValue;
        }

        c.value = extrapolatedValue;

        return c;
    }


    private boolean allElementsZero(List<Long> inputList) {
        return inputList.stream().allMatch(i -> i.equals(0L));
    }

    class Container {
        List<Long> longList;
        Long value;
        Boolean complete;

        public Container(List<Long> longList, Long value, boolean complete) {
//            System.out.println(longList + " " + value);
            this.longList = longList;
            this.value = value;
            this.complete = complete;
        }
    }
}
