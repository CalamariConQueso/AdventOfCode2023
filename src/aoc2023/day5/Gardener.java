package aoc2023.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gardener {

    private List<Long> seedList = new ArrayList<>();
    private List<SourceDestinationMap> mapList = new ArrayList<>();


    public void parse(List<String> instructions) {
        SourceDestinationMap map = null;
        for (String  line : instructions) {
            if (line.startsWith("seeds:")) {
                // Populate seedList
                String[] lineParts = line.split(": ");
                String[] seedArray = lineParts[1].split(" ");
                for (String s : seedArray) {
                    seedList.add(Long.parseLong(s));
                }
            } else if (line.endsWith(" map:")) {
                String [] mapInfo = line.split(" ");
                String[] nameInfo = mapInfo[0].split("-");
                map = new SourceDestinationMap(nameInfo[0], nameInfo[2]);
            } else if (line.isEmpty()) {
                if (map != null) {
                    mapList.add(map);
                    map = null;
                }
            } else {
                String[] lineParts = line.split(" ");
                if (map != null) {
                    map.addConverter(Long.parseLong(lineParts[0]), Long.parseLong(lineParts[1]), Long.parseLong(lineParts[2]));
                }
            }
        }
        // add final map to list
        if (map != null) {
            mapList.add(map);
        }

        System.out.println(seedList);

        SourceDestinationMap seedMap = getMapForSource("seed");
        Long lowestLocation = null;
        for (Long seed : seedList) {
//            System.out.println("--- Seed " + seed + " ---");
            Long location = findLocationForValue(seedMap, seed);
//            System.out.println("Seed " + seed + " is at location " + location);
            if (lowestLocation == null) {
                lowestLocation = location;
            } else {
                lowestLocation = location < lowestLocation ? location : lowestLocation;
            }
        }
//        Long location = findLocationForValue(seedMap, seedList.get(0));
//        System.out.println("Seed " + seedList.get(0) + " location: " + location);
        System.out.println("Lowest Location: " + lowestLocation);

        // Part 2 - seedList values are startNumber/rainge pairs
        lowestLocation = null;
        Long seedCount = 0L;
        for (int i = 0 ; i < seedList.size(); i +=2 ) {
            Long startingSeed = seedList.get(i);
            long rangeSize = seedList.get(i+1);
            for (Long seed = startingSeed; seed < (startingSeed + rangeSize); seed++) {
                if (seedCount % 1000000 == 0) {
                    System.out.println("Current seedCount: " + seedCount);
                }
                Long location = findLocationForValue(seedMap, seed);
                if (lowestLocation == null) {
                    lowestLocation = location;
                } else {
                    lowestLocation = location < lowestLocation ? location : lowestLocation;
                }
                seedCount++;
            }
        }
        System.out.println("Total Seeds: " + seedCount);
        System.out.println("Lowest Location: " + lowestLocation);
    }

    private Long findLocationForValue(SourceDestinationMap map, Long value) {
        Long newValue = map.getDestinationForValue(value);

//        System.out.println("Requires " + map.destination + " " + newValue);
        if ("location".equals(map.destination)) {
            return newValue;
        } else {
            SourceDestinationMap nextMap = getMapForSource(map.destination);
            return findLocationForValue(nextMap, newValue);
        }
    }

    private SourceDestinationMap getMapForSource(String source) {
        for (SourceDestinationMap map : mapList) {
            if (map.source.equals(source)) {
                return map;
            }
        }
        return null;
    }

    class SourceDestinationMap  {
        private List<Converter> converterList = new ArrayList<>();
        final String source;
        final String destination;
        public SourceDestinationMap(String source, String destination) {
            this.source = source;
            this.destination = destination;
        }

        public void addConverter(Long destination, Long source, Long range) {
            converterList.add(new Converter(destination, source, range));
        }

        public Long getDestinationForValue(Long value) {
            for (Converter converter : converterList) {
                if (converter.canMapValue(value)) {
                    return converter.getDestinationForValue(value);
                }
            }
            return value;
        }
    }


    class Converter {
        final Long source;
        final Long destination;
        final Long range;

        public Converter(Long destination, Long source, Long range) {
            this.source = source;
            this.destination = destination;
            this.range = range;

        }

        public boolean canMapValue(Long value) {
            return (value >= source && value < (source + range));
        }

        public Long getDestinationForValue(Long value) {
            if (canMapValue(value)) {
                Long difference = value - source;
                return destination + difference;
            }
            return value;
        }
    }

}
