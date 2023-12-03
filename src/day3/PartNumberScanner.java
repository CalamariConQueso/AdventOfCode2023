package day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import util.Point;


public class PartNumberScanner {

    List<String> nonSymbols = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."));
    List<String> digits = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    String[][] schematicGrid = new String[140][140];
    Map<Integer, List<Point>> partLocationMap = new HashMap<Integer, List<Point>>();

    // Location of all of the * symbols
    List<Point> sprocketList = new ArrayList<Point>();
    
    public void parse(List<String> schematicLines) {
        int x = 0;
        for (String schematicLine : schematicLines) {
            int y = 0;
            String partialPartNumber = "";
            for (char c : schematicLine.toCharArray()) {
                String s = Character.toString(c);

                // populate grid
                schematicGrid[x][y] = s;

                // populate sprocketList
                if ("*".equals(s)) {
                    // add to sprocketList
                    sprocketList.add(new Point(x, y));
                }

                // get part numbers
                switch (s) {
                    case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                        // System.out.println("Found Digit: " + s);
                        partialPartNumber = partialPartNumber.concat(s);
                    }
                    default -> {
                        if (!partialPartNumber.equals("")) {
                            // System.out.println("End of PartNo: " + partialPartNumber);
                            addPartNumber(partialPartNumber, new Point(x, y - (partialPartNumber.length())));
                            partialPartNumber = "";
                        }
                    }
                }

                // if we're at the end of the line, and there's a value in partialPartNumber
                if (y >= 139 && !partialPartNumber.equals("")) {
                    System.out.println("End of Line: " + partialPartNumber);
                    addPartNumber(partialPartNumber, new Point(x, y - (partialPartNumber.length() -1)));
                }
                
                y++;
            }
            x++;
        }

        // now both schematicGrid and partNumberLocationMap should be populated
        // System.out.println("Part Numbers: " );
        int total = 0;
        for (Entry<Integer, List<Point>> entry : partLocationMap.entrySet()) {
            List<Point> pointList = entry.getValue();
            String points = "";
            for (Point p : pointList) {
                points = points + p + " ";

                if (adjacentToSymbol(entry.getKey(), p)) {
                    // System.out.println(entry.getKey() + ": Adjacent");
                    total += entry.getKey();

                } else {
                    // System.out.println(entry.getKey() + ": Not Adjacent " + p);
                }
            }
            // System.out.println(entry.getKey() + ": " + points);
        }
        System.out.println("Total: " + total);


        // Part 2 Sprocket/gear stuff
        int totalGearRatio = 0;

        System.out.println("Total Sprockets: " + sprocketList.size());

        // for each sprocket, get a list of adjacent parts.
        int index = 1;
        for (Point sprocketPoint : sprocketList) {
            List<Integer> adjacentParts = getAdjacentParts(sprocketPoint);
            // if there are at least 2 adjacent parts, this sprocket is a gear!
            if (adjacentParts.size() >= 2) {
                int gearRatio = 0;
                String partString = "";
                for (Integer partNo : adjacentParts) {
                    partString += partNo + ", ";
                    if (gearRatio == 0) {
                        gearRatio = partNo;
                    } else {
                        gearRatio = gearRatio * partNo;
                    }
                }
                System.out.println(index + ": Sprocket at " + sprocketPoint + " is a gear (" + partString + " Gear Ratio: " + gearRatio + ")");
                totalGearRatio += gearRatio;
            } else {
                System.out.println(index + ": Sprocket at " + sprocketPoint + " is not a gear.");
            }

            // break;
            index++;
        }

        System.out.println("Total Gear Ratio: " + totalGearRatio);

    }

    private void addPartNumber(String partNo, Point location) {
        List<Point> inventory = partLocationMap.get(Integer.parseInt(partNo));

        if (inventory != null) {
            inventory.add(location);
        } else {
            List<Point> newList = new ArrayList<Point>();
            newList.add(location);
            partLocationMap.put(Integer.parseInt(partNo), newList);
        }
    }

    private boolean adjacentToSymbol(Integer partNo, Point point) {

        int minX = point.getX() - 1;
        int maxX = point.getX() + 1;
        int minY = point.getY() - 1;
        int maxY = point.getY() + partNo.toString().length() + 1;

        if (minX < 0 ) {
            minX = 0;
        }
        if (maxX > 139) {
            maxX = 139;
        }
        if (minY < 0 ) {
            minY = 0;
        }
        if (maxY > 139) {
            maxY = 139;
        }

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                String s = schematicGrid[x][y];
                if (!nonSymbols.contains(s)) {
                    // character must be a symbol
                    return true;
                }
            }
        }

        return false;
    }

    private List<Integer> getAdjacentParts(Point sprocketPoint) {

        Point testPoint = new Point(47, 136);
        if (sprocketPoint.equals(testPoint)) {
            System.out.println("Finding adjacent parts for point: " + sprocketPoint);
        }

        List<Integer> adjacentParts = new ArrayList<Integer>();

        int minX = sprocketPoint.getX() - 1;
        int maxX = sprocketPoint.getX() + 1;
        int minY = sprocketPoint.getY() - 1;
        int maxY = sprocketPoint.getY() + 1;

        if (minX < 0 ) {
            minX = 0;
        }
        if (maxX > 139) {
            maxX = 139;
        }
        if (minY < 0 ) {
            minY = 0;
        }
        if (maxY > 139) {
            maxY = 139;
        }

        if (sprocketPoint.equals(testPoint)) {
            System.out.println("Scan Range: ");
            System.out.println("minX: " + minX + ", maxX: " + maxX + " ; minY: " + minY + ", maxY: " + maxY);
        }

        Set<PartLocationPair> gearPartSet = new HashSet<PartLocationPair>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                String s = schematicGrid[x][y];
                if (digits.contains(s)) {
                    if (sprocketPoint.equals(testPoint)) {
                        System.out.println("Digit Detected: " + s);
                    }
                    // Adjacent to a part number
                    PartLocationPair part = getPartAtLocation(x, y);
                    if (part != null) {
                        gearPartSet.add(part);
                    }
                }
            }
        }

        for (PartLocationPair plp : gearPartSet) {
            adjacentParts.add(plp.partNo);
        }

        return adjacentParts;
    }

    private PartLocationPair getPartAtLocation(int x, int y) {
        // Find start location of this part
        int partStartY = y;
        while (digits.contains(schematicGrid[x][partStartY])) {
            if (partStartY == 0) {
                break;
            }
            partStartY--;
        }
        // add back the final decrement
        if (partStartY > 0 ) {
            partStartY++;
        }
        if (partStartY == 0 && !digits.contains(schematicGrid[x][partStartY])) {
            partStartY++;
        }

        // System.out.println("Part starting point: " + x + ", " + partStartY);

        // Now find the part that contains the point (x, partStartY)
        Point partToFind = new Point(x, partStartY);

        for (Entry<Integer, List<Point>> entry : partLocationMap.entrySet()) {
            for (Point point : entry.getValue()) {

                if (point.equals(partToFind)) {
                    System.out.println("Found part " + entry.getKey() + " at " + point);
                    return new PartLocationPair(entry.getKey(), point);
                }
            }
        }
        return null;
    }

}
