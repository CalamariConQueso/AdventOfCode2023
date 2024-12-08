package aoc2023.day10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Point;

public class PipeLoop {
    List<List<PipeNode>> pipeMap = new ArrayList<>();
    List<Point> mainLoopPoints = new ArrayList<>();

    public void parse(List<String> pipeList) {
        int rowNo = 0;
        for(String line : pipeList) {
            List<PipeNode> row = new ArrayList<>();
            char[] lineChars = line.toCharArray();
            for (int columnNo = 0; columnNo < lineChars.length; columnNo++) {
                char c = lineChars[columnNo];
                if (c == 'S') {
                    // starting point
                    System.out.println("Starting Point is Row: " + rowNo + " Column: " + columnNo);
                    mainLoopPoints.add(new Point(columnNo, rowNo));
                }
                PipeNode node = new PipeNode(new Point(columnNo, rowNo), c);
                row.add(node);
            }
            pipeMap.add(row);
            rowNo++;
        }
        
        // Start at starting point (only point in mainLoopPoints) and follow the loop.. adding each new point to mainLoopPoints
        Point startingPoint = mainLoopPoints.get(0);
        Point currentLocation = startingPoint;
        do {
            currentLocation = findNextPipe(currentLocation);
            if (currentLocation != startingPoint) {
                if (currentLocation == null) {
                    System.out.println("currentLocation is NULL");
                }
                mainLoopPoints.add(currentLocation);
            }
        } while (!currentLocation.equals(startingPoint));

        System.out.println("mainLoopPoints.size(): " + mainLoopPoints.size());
        System.out.println("midPoint: " + (mainLoopPoints.size()/2));;



        // remove junk pipes
        for(List<PipeNode> row : pipeMap) {
            for (PipeNode n : row) {
                if (!mainLoopPoints.contains(n.location)) {
                    n.c = '.';
                    if (n.location.getX() == 0) {
                        n.c = ' ';
                    } else if (n.location.getX() == 139) {
                        n.c = ' ';
                    } else if (n.location.getY() == 0) {
                        n.c = ' ';
                    } else if (n.location.getY() == 139) {
                        n.c = ' ';
                    } else if (hasAdjacentSpace(n.location)) {
                        n.c = ' ';
                    }
                }
            }
        }
        for (int y = 138; y > 0; y--) {
            for (int x = 138; x > 0; x--) {
                PipeNode n = pipeMap.get(y).get(x);
                if (!mainLoopPoints.contains(n.location)) {
                    if (hasAdjacentSpace(n.location)) {
                        n.c = ' ';
                    }
                }
            }
        }

        // we have a pretty clean map now
        int counter = 0;
        for(List<PipeNode> row : pipeMap) {
            for (PipeNode n : row) {
                if (n.c == '.') {
                    // determine if this space is inside the loop or not.
                    if (isInsideLoop(n.location)) {
                        counter++;
                    }
                }
            }
        }

        


        // Draw Loop
        for(List<PipeNode> row : pipeMap) {
            String rowString = "";
            for (PipeNode n : row) {
                rowString += Character.toString(n.c);
            }
            System.out.println(rowString);
        }
        

        System.out.println(counter);
    }
    
    private boolean isInsideLoop(Point p) {
        int edgeCount = 0;

        List<Character> charList = new ArrayList<>();

        for (int y = 0; y < p.getY(); y++) {
            PipeNode node = pipeMap.get(y).get(p.getX());
            if (node.c == '-') {
                edgeCount++;
            } else if (node.c == 'F' || node.c == 'J' || node.c == 'L' || node.c == '7') {
                charList.add(node.c);
            }

            int start = 0;
            int end = charList.size() - 1;
            
            // int f = Collections.frequency(charList, 'F');
            // int j = Collections.frequency(charList, 'J');
            // int l = Collections.frequency(charList, 'L');
            // int seven = Collections.frequency(charList, '7');
            
  

            while (start < end) {
            
                char a = charList.get(end);
                char b = charList.get(start);
                // creating a string from the last and first elements of the list.  no idea what I'm doing.
                String ab = Character.toString(a) + Character.toString(b);
                // I guess we're looking for "JF, FJ, 7L, or L7"
                if ("JFJ_7L7".indexOf(ab) >= 0) {
                    edgeCount++;
                }
                start++;
                end--;
            }


        }

        return edgeCount % 2 == 1;
    }

    private boolean hasAdjacentSpace(Point p) {
        PipeNode adj1 = pipeMap.get(p.getY() + 1).get(p.getX());
        PipeNode adj2 = pipeMap.get(p.getY() - 1).get(p.getX());
        PipeNode adj3 = pipeMap.get(p.getY()).get(p.getX() + 1);
        PipeNode adj4 = pipeMap.get(p.getY()).get(p.getX() - 1);

        return (adj1.c == ' ' || adj2.c == ' ' || adj3.c == ' ' || adj4.c == ' ');
    }

    private Point findNextPipe(Point currentLocation) {
        
        PipeNode node = pipeMap.get(currentLocation.getY()).get(currentLocation.getX());
        // System.out.println("CurrentLocation: " + currentLocation + ": " + node.c);
        Point nextPoint = null;

        // check up
        if (nextPoint == null && node.connects(PipeDir.UP)) {
            PipeNode nextNode = pipeMap.get(currentLocation.getY() - 1).get(currentLocation.getX());
            if (nextNode.c == 'S') {
                return nextNode.location;
            }
            if (nextNode.connects(PipeDir.DOWN) && !mainLoopPoints.contains(nextNode.location)) {
                nextPoint = nextNode.location;
                // System.out.println("UP: " + nextNode.c);
            }
        }
        if (nextPoint == null && node.connects(PipeDir.RIGHT)) {
            PipeNode nextNode = pipeMap.get(currentLocation.getY()).get(currentLocation.getX() + 1);
            if (nextNode.c == 'S') {
                return nextNode.location;
            }
            if (nextNode.connects(PipeDir.LEFT) && !mainLoopPoints.contains(nextNode.location)) {
                nextPoint = nextNode.location;
                // System.out.println("RIGHT: " + nextNode.c);
            }
        }
        if (nextPoint == null && node.connects(PipeDir.DOWN)) {
            PipeNode nextNode = pipeMap.get(currentLocation.getY() + 1).get(currentLocation.getX());
            if (nextNode.c == 'S') {
                return nextNode.location;
            }
            if (nextNode.connects(PipeDir.UP) && !mainLoopPoints.contains(nextNode.location)) {
                nextPoint = nextNode.location;
                // System.out.println("DOWN: " + nextNode.c);
            }
        }
        if (nextPoint == null && node.connects(PipeDir.LEFT)) {
            PipeNode nextNode = pipeMap.get(currentLocation.getY()).get(currentLocation.getX() - 1);
            if (nextNode.c == 'S') {
                return nextNode.location;
            }
            if (nextNode.connects(PipeDir.RIGHT) && !mainLoopPoints.contains(nextNode.location)) {
                nextPoint = nextNode.location;
                // System.out.println("LEFT: " + nextNode.c);
            }
        }

        return nextPoint;
    }


    class PipeNode {
        Character c;
        Point location;

        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;

        public PipeNode(Point location, Character c) {
            this.location = location;
            this.c = c;

            buildConnections();
        }

        public boolean connects(PipeDir direction) {
            if (PipeDir.UP.equals(direction) && up && location.getY() > 0) {
                return true;
            } else if (PipeDir.DOWN.equals(direction) && down && location.getY() < 139) {
                return true;
            } else if (PipeDir.RIGHT.equals(direction) && right && location.getX() < 139) {
                return true;
            } else if (PipeDir.LEFT.equals(direction) && left && location.getX() > 0) {
                return true;
            }
            return false;
        }

        private void buildConnections() {
            switch (c) {
                case '|' -> { up = true; down = true; }
                case '-' -> { right = true; left = true; }
                case '7' -> { down = true; left = true; }
                case 'F' -> { down = true; right = true; }
                case 'J' -> { up = true; left = true; }
                case 'L' -> { up = true; right = true; }
                case 'S' -> { up = true; down = true; left = true; right = true; }
            }
        }

    }

    enum PipeDir {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

}
