package day3;

import util.Point;

public class PartLocationPair {

    public Integer partNo;
    public Point location;

    public PartLocationPair(Integer partNo, Point location) {
        this.partNo = partNo;
        this.location = location;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((partNo == null) ? 0 : partNo.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PartLocationPair other = (PartLocationPair) obj;
        if (partNo == null) {
            if (other.partNo != null)
                return false;
        } else if (!partNo.equals(other.partNo))
            return false;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        return true;
    }
    
}
