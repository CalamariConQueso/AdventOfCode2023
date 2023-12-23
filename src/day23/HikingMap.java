package day23;

import util.AoCGrid;
import util.Location;
import util.Point;

import java.util.ArrayList;
import java.util.List;

public class HikingMap {
    private AoCGrid<TrailNode> hikingTrails= new AoCGrid<>();
    private Long nextPathId = 0L;

    private Long longestRoute = 0L;

    public void parse(List<String> inputList) {
        int y = 0;
        for (String row : inputList) {
            char[] chars = row.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                Point p = new Point(x, y);
                // System.out.println(p);
                hikingTrails.add(new TrailNode(p, chars[x]));
            }
            y++;
        }

        hikingTrails.printGrid();

        TrailNode destination = findDestinationTrailNode();
        
        Long pathId = getNextPathId();
        List<Point> pathPointList = new ArrayList<>();

        walkPath(pathId, pathPointList, findStartingTrailNode(), 0L, destination, 1);

        System.out.println("Longest Route: " + longestRoute);
        longestRoute = 0L;
        pathPointList = new ArrayList<>();
        nextPathId = 0L;
        pathId = getNextPathId();

        walkPath(pathId, pathPointList, findStartingTrailNode(), 0L, destination, 2);

        System.out.println("Longest Route part 2: " + longestRoute);


    }

    private void walkPath(Long pathId, List<Point> pathPointList, TrailNode node, Long steps, TrailNode destination, int part) {
        
        if (node.getLocation().equals(destination.getLocation())) {
            // System.out.println("Arrived at destination.  PathId: " + pathId + " Steps: " + steps);
            longestRoute = steps > longestRoute ? steps : longestRoute;
            return;
        }
        if (pathPointList.contains(node.getLocation())) {
            return;
        }
        pathPointList.add(node.getLocation());
        List<Direction> directions = null;
        if (part == 1) {
            directions = node.enter(pathId);
        } else if (part == 2) {
            directions = node.enter2(pathId);
        }

        if (directions.isEmpty()) {
            return;
        } 
        // System.out.println(pathId + " " + directions + " " + steps);

        boolean createNewPathId = false;
        for (Direction d : directions) {

            TrailNode nextNode = null;
            switch (d) {
                case Direction.NORTH -> { nextNode = hikingTrails.get(node.getLocation().getX(), node.getLocation().getY() - 1); }
                case Direction.SOUTH -> { nextNode = hikingTrails.get(node.getLocation().getX(), node.getLocation().getY() + 1 ); }
                case Direction.EAST -> { nextNode = hikingTrails.get(node.getLocation().getX() + 1, node.getLocation().getY()); }
                case Direction.WEST -> { nextNode = hikingTrails.get(node.getLocation().getX() - 1, node.getLocation().getY()); }
            }
            if (nextNode != null) {
                Long newStepCount = steps + 1;

                try {
                    if (createNewPathId) {
                        pathId = getNextPathId();
                        ArrayList<Point> pointListCopy = new ArrayList<Point>(pathPointList);
                        walkPath(pathId, pointListCopy, nextNode, newStepCount, destination, part);
                    } else {
                        walkPath(pathId, pathPointList, nextNode, newStepCount, destination, part);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
            }
            
            createNewPathId = true;
        }

    }

    private Long getNextPathId() {
        return ++nextPathId;
    }

    private TrailNode findStartingTrailNode() {
        for (int x = 0; x < hikingTrails.sizeX(); x++) {
            TrailNode node = hikingTrails.get(x, 0);
            if (node.getSymbol() == '.') {
                return node;
            }
        }
        return null;
    }

    private TrailNode findDestinationTrailNode() {
        for (int x = 0; x < hikingTrails.sizeX(); x++) {
            TrailNode node = hikingTrails.get(x, hikingTrails.sizeY() - 1 );
            if (node.getSymbol() == '.') {
                return node;
            }
        }
        return null;
    }


    class TrailNode extends Location {

        private Character symbol;

        public TrailNode(Point location, Character symbol) {
            super(location);
            this.symbol = symbol;
        }

        // returns list of directions that can be traveled to from this point
        public List<Direction> enter(Long pathId) {
            List<Direction> trails = new ArrayList<Direction>();

            switch(symbol) {
                // > < ^ and v characters only allow travel in one direction
                case '>' -> { 
                    trails.add(Direction.EAST);
                }
                case '<' -> {
                    trails.add(Direction.WEST);
                }
                case '^' -> {
                    trails.add(Direction.NORTH);
                }
                case 'v' -> {
                    trails.add(Direction.SOUTH);
                }
                case '.' -> {
                    // analyze surrounding points
                    TrailNode north = hikingTrails.get(getLocation().getX(), getLocation().getY() - 1);
                    TrailNode south = hikingTrails.get(getLocation().getX(), getLocation().getY() + 1);
                    TrailNode east = hikingTrails.get(getLocation().getX() + 1, getLocation().getY());
                    TrailNode west = hikingTrails.get(getLocation().getX() - 1, getLocation().getY());

                    if (north != null && north.getSymbol() != '#') {
                        trails.add(Direction.NORTH);
                    }
                    if (south != null && south.getSymbol() != '#') {
                        trails.add(Direction.SOUTH);
                    }
                    if (east != null && east.getSymbol() != '#') {
                        trails.add(Direction.EAST);
                    }
                    if (west != null && west.getSymbol() != '#') {
                        trails.add(Direction.WEST);
                    }
                }
            }

            return trails;
        }

        public List<Direction> enter2(Long pathId) {
            List<Direction> trails = new ArrayList<Direction>();

            switch(symbol) {
                case '.', '>', '<', '^', 'v' -> {
                    // analyze surrounding points
                    TrailNode north = hikingTrails.get(getLocation().getX(), getLocation().getY() - 1);
                    TrailNode south = hikingTrails.get(getLocation().getX(), getLocation().getY() + 1);
                    TrailNode east = hikingTrails.get(getLocation().getX() + 1, getLocation().getY());
                    TrailNode west = hikingTrails.get(getLocation().getX() - 1, getLocation().getY());

                    if (north != null && north.getSymbol() != '#') {
                        trails.add(Direction.NORTH);
                    }
                    if (south != null && south.getSymbol() != '#') {
                        trails.add(Direction.SOUTH);
                    }
                    if (east != null && east.getSymbol() != '#') {
                        trails.add(Direction.EAST);
                    }
                    if (west != null && west.getSymbol() != '#') {
                        trails.add(Direction.WEST);
                    }
                }
            }

            return trails;
        }
        

        public Character getSymbol() {
            return symbol;
        }

        @Override
        public String toString() {
            return Character.toString(symbol);
        }

    }

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

}
