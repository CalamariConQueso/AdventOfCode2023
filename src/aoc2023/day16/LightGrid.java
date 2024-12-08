package aoc2023.day16;

import util.AoCGrid;
import util.Location;
import util.Point;

import java.util.ArrayList;
import java.util.List;

public class LightGrid {
    AoCGrid<LightGridTile> grid = new AoCGrid<>();

    List<Point> energized = new ArrayList<>();

    public void parse(List<String> inputList) {
        int y = 0;
        for (String row : inputList) {
            char[] chars = row.toCharArray();
            for (int x = 0; x < chars.length; x++) {
                Point p = new Point(x, y);
//                System.out.println(p);
                grid.add(new LightGridTile(p, chars[x]));
            }
            y++;
        }

        // Part 1
        // light comes from top right from the left
        shineLight(new Point(0, 0), Direction.LEFT);
        System.out.println("(p1) Energized cells: " + energized.size());
        energized.clear();

        // Part 2
        // light can come from any edge, find highest
        List<Integer> energizedCountList = new ArrayList<>();

        // From Left Side
        for (y = 0; y < grid.sizeY(); y++) {
            resetTiles();
            shineLight(new Point(0, y), Direction.LEFT);
            energizedCountList.add(energized.size());
            energized.clear();
        }
        // From Right Side
        for (y = 0; y < grid.sizeY(); y++) {
            resetTiles();
            shineLight(new Point(grid.sizeX() - 1, y), Direction.RIGHT);
            energizedCountList.add(energized.size());
            energized.clear();
        }
        // From Top
        for (int x = 0; x < grid.sizeX(); x++) {
            resetTiles();
            shineLight(new Point(x, 0), Direction.UP);
            energizedCountList.add(energized.size());
            energized.clear();
        }

        // From Bottom
        for (int x = 0; x < grid.sizeX(); x++) {
            resetTiles();
            shineLight(new Point(x, grid.sizeY() -1), Direction.DOWN);
            energizedCountList.add(energized.size());
            energized.clear();
        }

        int max = energizedCountList.get(0);
        for (Integer value : energizedCountList) {
            max = (value > max ? value : max);
        }

        System.out.println("(p2) Max Energized cells: " + max);
    }
    private void resetTiles() {
        for (int y = 0; y < grid.sizeY(); y++) {
            for (int x = 0; x < grid.sizeX(); x++) {
                LightGridTile tile = grid.get(x, y);
                if (tile != null) {
                    tile.reset();
                }
            }
        }
    }

    private void shineLight(Point p, Direction source) {
        if (!grid.pointInBounds(p)) {
            return;
        }
        if (!energized.contains(p)) {
            energized.add(p);
//            System.out.println("Energized Size: " + energized.size());
        }
        LightGridTile tile = grid.get(p);
        if (tile != null) {
            if (tile.reflectedFromList.contains(source)) {
                return;
            }
            Direction reflect = tile.reflectLight(source);
            switch (reflect) {
                case RIGHT -> {
                    shineLight(tile.getPointForDirection(Direction.RIGHT), Direction.LEFT);
                }
                case LEFT -> {
                    shineLight(tile.getPointForDirection(Direction.LEFT), Direction.RIGHT);
                }
                case UP -> {
                    shineLight(tile.getPointForDirection(Direction.UP), Direction.DOWN);
                }
                case DOWN -> {
                    shineLight(tile.getPointForDirection(Direction.DOWN), Direction.UP);
                }
                case RIGHT_AND_LEFT -> {
                    shineLight(tile.getPointForDirection(Direction.RIGHT), Direction.LEFT);
                    shineLight(tile.getPointForDirection(Direction.LEFT), Direction.RIGHT);
                }
                case UP_AND_DOWN -> {
                    shineLight(tile.getPointForDirection(Direction.UP), Direction.DOWN);
                    shineLight(tile.getPointForDirection(Direction.DOWN), Direction.UP);
                }
            }
        }
    }


    class LightGridTile extends Location {
        boolean energized = false;
        int energizedCount = 0;
        List<Direction> reflectedFromList = new ArrayList<>();
        Character symbol;
        public LightGridTile(Point location, Character symbol) {
            super(location);
            this.symbol = symbol;
        }

        public void reset() {
            energized = false;
            reflectedFromList.clear();
        }

        public Point getPointForDirection(Direction destination) {
            Point p = new Point (getLocation().getX(), getLocation().getY());

            switch (destination) {
                case UP -> { p.setY(getLocation().getY() - 1); }
                case DOWN -> { p.setY(getLocation().getY() + 1); }
                case LEFT -> { p.setX(getLocation().getX() - 1); }
                case RIGHT -> { p.setX(getLocation().getX() + 1); }
                default -> { p = null; }
            }
            return p;
        }

        public Direction reflectLight(Direction source) {
            energized = true;
            if (!reflectedFromList.contains(source)) {
                reflectedFromList.add(source);
            }
            switch (source) {
                case RIGHT -> {
                    switch (symbol) {
                        case '.', '-' -> { return Direction.LEFT; }
                        case '|' -> { return Direction.UP_AND_DOWN; }
                        case '/' -> { return Direction.DOWN; }
                        case '\\' -> { return Direction.UP; }
                    }
                }
                case LEFT -> {
                    switch (symbol) {
                        case '.', '-' -> { return Direction.RIGHT; }
                        case '|' -> { return Direction.UP_AND_DOWN; }
                        case '/' -> { return Direction.UP; }
                        case '\\' -> { return Direction.DOWN; }
                    }
                }
                case UP -> {
                    switch (symbol) {
                        case '-' -> { return Direction.RIGHT_AND_LEFT; }
                        case '.', '|' -> { return Direction.DOWN; }
                        case '/' -> { return Direction.LEFT; }
                        case '\\' -> { return Direction.RIGHT; }
                    }
                }
                case DOWN -> {
                    switch (symbol) {
                        case '-' -> { return Direction.RIGHT_AND_LEFT; }
                        case '.', '|' -> { return Direction.UP; }
                        case '/' -> { return Direction.RIGHT; }
                        case '\\' -> { return Direction.LEFT; }
                    }
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return Character.toString(symbol);
        }
    }

    enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        RIGHT_AND_LEFT,
        UP_AND_DOWN
    }
}
