package util;

import java.util.ArrayList;
import java.util.List;

/*
Generic grid, with 0,0 being the top right element
 */

public class AoCGrid<T extends Location> {
    List<List<T>> grid;

    public AoCGrid() {
        grid = new ArrayList<>();
    }

    public void add(T tile) {
        // make sure we have enough rows
        Point location = tile.getLocation();
        if (grid.size() <= location.getY()) {
            for (int i = grid.size(); i <= location.getY(); i++) {
                grid.add(new ArrayList<>());
            }
        }
        // Make sure our row has enough elements
        List<T> row = grid.get(location.getY());
        if (row.size() < location.getX()) {
            for (int i = 0; i <= (location.getX()); i++) {
                row.add(null);
            }
        }
        // remove whatever is there, if it exists.
        try {
            row.remove(location.getX());
        } catch (IndexOutOfBoundsException e) { }

        row.add(location.getX(), tile);
    }

    public int sizeY() {
        return grid.size();
    }
    public int sizeX() {
        return grid.get(0).size();
    }
    public T get(Point location) {
        if (pointInBounds(location)) {
            return grid.get(location.getY()).get(location.getX());
        }
        return null;
    }

    public T get(int x, int y) {
        return get(new Point(x, y));
    }

    public boolean pointInBounds(Point p) {
        // this assumes all rows have the same length
        if ( p.getY() < 0 || p.getY() >= sizeY() || grid.size() < p.getY()) {
            return false;
        } else if (p.getX() < 0 || p.getX() >= sizeX() || grid.get(0).size() < p.getX()) {
            return false;
        }
        return true;
    }

    public void printGrid() {
        for (List<T> row : grid) {
            String rowString = "";
            for (T tile : row) {
                rowString += tile.toString();
            }
            System.out.println(rowString);
        }
    }
}
