package aoc2023.day12;

import java.util.List;

public class SpringRecord {

    List<Record> records;
    public void parse(List<String> recordLines) {
        for(String line : recordLines) {
            String[] lineParts = line.split("\\s+");

        }

    }



    class Record {
        String springs;
        List<Integer> blockSizes;
        public void Record(String springs, List<Integer> blockSizes) {
            this.springs = springs;
            this.blockSizes = blockSizes;
        }


    }
}
