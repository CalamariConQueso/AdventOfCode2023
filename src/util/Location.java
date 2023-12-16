package util;

public class Location {
    Point location;

    public Location(Point location) {
        setLocation(location);
    }

    public Point getLocation() {
        return location;
    };

    public void setLocation(Point location) {
        this.location = location;
    }
}
