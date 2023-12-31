package util;

public class Pair<L, R> {
    
    public L left;
    public R right;
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
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
        if (left.getClass() == right.getClass()) {
            if (left.equals(right) && right.equals(left)) {
                return true;
            }
        } else {
            Pair<L, R> other = (Pair<L, R>) obj;
            if (left == null) {
                if (other.left != null)
                    return false;
            } else if (!left.equals(other.left))
                return false;
            if (right == null) {
                if (other.right != null)
                    return false;
            } else if (!right.equals(other.right))
                return false;
        }
        return true;
    }

    
}
