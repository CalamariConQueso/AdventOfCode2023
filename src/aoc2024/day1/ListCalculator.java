package aoc2024.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListCalculator {
    private List<Integer> leftList;
    private List<Integer> rightList;

    public ListCalculator(List<String> inputList) {
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();

        for (String line : inputList) {
//            System.out.println("line: " + line);
            String[] values = line.split("\\s+");
//            System.out.println(values[0] + " " + values[1]);
            leftList.add(Integer.parseInt(values[0]));
            rightList.add(Integer.parseInt(values[1]));
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        long distance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            distance += Math.abs(leftList.get(i) - rightList.get(i));
        }
        System.out.println("distance: " + distance);

        long similarity = 0;
        for (int i = 0; i < leftList.size(); i++) {
            Integer v = leftList.get(i);
            similarity += (v * timesInRightList(v));
        }
        System.out.println("similarity: " + similarity);

    }

    private long timesInRightList(Integer i) {
        return Collections.frequency(rightList, i);
    }

}
