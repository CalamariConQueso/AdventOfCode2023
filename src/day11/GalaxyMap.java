package day11;

import java.util.ArrayList;
import java.util.List;

import util.Pair;
import util.Point;

public class GalaxyMap {
    
    List<List<Character>> galaxyList = new ArrayList<>();
    List<Point> galaxyLocationList = new ArrayList<>();

    List<Pair<Point, Point>> galaxyPairs = new ArrayList<>();
    
    public void parse(List<String> lineStrings) {
        for (String line : lineStrings) {
            List<Character> row = new ArrayList<>();
            for (Character c : line.toCharArray()) {
                row.add(c);
            }

            galaxyList.add(row);
            // Add extra row if this row has no galaxies
            if (!line.contains("#")) {
                List<Character> newRow = new ArrayList<>();
                for (int i = 0; i < row.size(); i++) {
                    newRow.add('+');
                }
                galaxyList.add(newRow);
            }
        }

        // expand any column with no galaxy
        for (int i = galaxyList.get(0).size() - 1; i >= 0; i--) {
            if (columnHasNoGalaxies(i)) {
                expandColumn(i);
            }
        }

        // Add each galaxy to the galaxyDistanceMap
        for (int y = 0; y < galaxyList.size(); y++) {
            List<Character> row = galaxyList.get(y);
            for (int x = 0; x < row.size(); x++) {
                if (row.get(x) == '#') {
                    galaxyLocationList.add(new Point(x, y));
                }
            }
        }

        buildPairs(0);

        System.out.println("galaxyPairs: " + galaxyPairs.size());

        Long distance = 0L;
        for (Pair<Point, Point> p : galaxyPairs) {
            distance += calculateDistancePart1(p.left, p.right);
        }
        System.out.println("Distance Part 1: " + distance);
        
        distance = 0L;
        for (Pair<Point, Point> p : galaxyPairs) {
            distance += calculateDistancePart2(p.left, p.right);
        }
        System.out.println("Distance Part 2: " + distance);
        
        for (List<Character> row : galaxyList) {
            String rowString = "";
            for (Character c : row) {
                rowString += Character.toUpperCase(c);
            }
            System.out.println(rowString);
        }

    }

    private Long calculateDistancePart1(Point left, Point right) {
        Long distance = 0L;

        int minX = Math.min(left.getX(), right.getX());
        int maxX = Math.max(left.getX(), right.getX());
        int minY = Math.min(left.getY(), right.getY());
        int maxY = Math.max(left.getY(), right.getY());

        distance += (maxX - minX) + (maxY - minY);

        return distance;
    }

    private Long calculateDistancePart2(Point left, Point right) {

        Long distance = 0L;

        int minX = Math.min(left.getX(), right.getX());
        int maxX = Math.max(left.getX(), right.getX());
        int minY = Math.min(left.getY(), right.getY());
        int maxY = Math.max(left.getY(), right.getY());

        distance += (maxX - minX) + (maxY - minY);

        for (int y = minY; y <= maxY; y++) {
            if (galaxyList.get(y).get(0) == '+') {
                distance += 999998;
            }            
        }

        for (int x = minX; x <= maxX; x++) {
            if (galaxyList.get(0).get(x) == '+') {
                distance += 999998;
            }
        }

        return distance;
    }

    private void buildPairs(int index) {
        if (index < galaxyLocationList.size()) {
            // System.out.println("buildPairs Index: " + index);
            Point left = (galaxyLocationList.get(index));

            for (int i = (index + 1); i < galaxyLocationList.size(); i++) {
                Point right = galaxyLocationList.get(i);
                if (!left.equals(right)) {
                    galaxyPairs.add(new Pair<Point, Point>(left, right));
                }
            }
            buildPairs(++index);
        }
    }

    private boolean columnHasNoGalaxies(int index) {
        for (List<Character> row : galaxyList) {
            if (row.get(index) == '#') {
                return false;
            }
        }
        return true;
    }

    private void expandColumn(int index) {
        // System.out.println("Expand column " + index);
        for (List<Character> row : galaxyList) {
            row.add(index, '+');
        }
    }


    class Galaxy {
        Point location;
        int id;
        public Galaxy(Point location, int id) {
            this.location = location;
            this.id = id;
        }
    }

}
