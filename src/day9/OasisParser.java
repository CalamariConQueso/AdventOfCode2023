package day9;

import java.util.ArrayList;
import java.util.List;

public class OasisParser {

    List<Long> extrapolations = new ArrayList<Long>();

    public void parse(List<String> readings) {
        for (String line : readings) {
            List<Long> readingList = new ArrayList<>();
            String[] lineArray = line.split("\\s+");
            for (String num : lineArray) {
                readingList.add(Long.parseLong(num));
            }
            
            // System.out.println(readingList);
            
            extrapolateList(readingList, null);
            

            // abort after first one
            // break;
        }
    }

    // private List<Long> extrapolateList(List<Long> inputList, Long extrapolatedValue) {
    //     List<Long> newList = new ArrayList<>();

    //     System.out.println(inputList);
    //     for(int i = 1; i < inputList.size(); i++) {
    //         newList.add(inputList.get(i) - inputList.get(i - 1));
    //     }
    //     if (extrapolatedValue == null) {
    //         extrapolatedValue = newList.getLast();
    //         System.out.println("Starting with: " + extrapolatedValue);
    //     } else {
    //         System.out.println("Adding " + newList.getLast() + " to " + extrapolatedValue);
    //         extrapolatedValue += newList.getLast();
    //     }

    //     System.out.println(extrapolatedValue);

    //     while (!allElementsZero(newList)) {
    //         newList = extrapolateList(newList, extrapolatedValue);
    //     }

    //     return newList;
    // }

    private Long extrapolateList(List<Long> inputList, Long extrapolatedValue) {
        List<Long> newList = new ArrayList<>();

        System.out.println(inputList);
        for(int i = 1; i < inputList.size(); i++) {
            newList.add(inputList.get(i) - inputList.get(i - 1));
        }
        System.out.println("newList: " + newList);
        if (extrapolatedValue == null) {
            extrapolatedValue = newList.getLast();
            System.out.println("Starting with: " + extrapolatedValue);
        } else {
            System.out.println("Adding " + newList.getLast() + " to " + extrapolatedValue);
            extrapolatedValue += newList.getLast();
        }

        // System.out.println(extrapolatedValue);

        while (!allElementsZero(newList)) {
            extrapolatedValue += extrapolateList(newList, extrapolatedValue);
        }

        System.out.println("Returning: " + extrapolatedValue);
        return extrapolatedValue;
    }

    private boolean allElementsZero(List<Long> inputList) {
        return inputList.stream().allMatch(i -> i.equals(0L));
    }

}
