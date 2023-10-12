import java.util.Iterator;

public class SnakeNode implements Iterable<SnakeNode> {
    int[] coordinate;
    SnakeNode next;

    public SnakeNode(int[] coordinate) {
        this.coordinate = coordinate;
        this.next = null;
    }

    public int[] coordinate() {
        return coordinate;
    }

    public void update(int[] coordinate, boolean addTail) {
        if (next != null) {
            next.update(this.coordinate, addTail);
        } else if (addTail) {
            next = new SnakeNode(this.coordinate);
        }
        this.coordinate = coordinate;
    }

    public void addTail(int[] coordinate) {
        if (next == null) {
            next = new SnakeNode(coordinate);
        } else {
            next.addTail(coordinate);
        }
    }

    public int length() {
        return 1 + (next != null ? next.length() : 0);
    }

    @Override
    public Iterator<SnakeNode> iterator() {
        return new SnakeIterator(this);
    }

    @Override
    public String toString() {
        return "[" + coordinate[0] + ", " + coordinate[1] + "]";
    }

    public String snakeToString() {
        return toString() + (next != null ? "<-" + next.snakeToString() : "");
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SnakeNode)) {
            return false;
        }
        SnakeNode newOther = (SnakeNode) other;
        return newOther.coordinate.equals(coordinate);
    }
}

class SnakeIterator implements Iterator<SnakeNode> {
    SnakeNode current;

    public SnakeIterator(SnakeNode node) {
        current = node;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public SnakeNode next() {
        SnakeNode data = current;
        current = current.next;
        return data;
    }
};