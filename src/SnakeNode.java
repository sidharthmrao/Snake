import java.util.Arrays;
import java.util.Iterator;

public class SnakeNode implements Iterable<SnakeNode> {
    int[] coordinate;
    SnakeNode next;

    /**
     * Create a new snake node with a coordinate. Takes the form of the head of a linked list.
     * @param coordinate Coordinate of the snake node. Must be of length 2.
     */
    public SnakeNode(int[] coordinate) {
        this.coordinate = coordinate;
        this.next = null;
    }

    /**
     * Get the coordinate of the snake node
     * @return An int[] representing the coordinate. Must be of length 2.
     */
    public int[] coordinate() {
        assert coordinate.length == 2;
        return coordinate;
    }

    /**
     * Update the entire snake from this node to the tail with a new coordinate. If addTail is
     * true, add a new tail node. Every node after this one will be updated with the previous
     * node's coordinate, and the new coordinate will be this node's coordinate.
     * @param coordinate New coordinate. Must be of distance 1 from the previous coordinate
     * @param addTail Whether to add a new tail node
     */
    public void update(int[] coordinate, boolean addTail) {
        assert coordinate != null;

        System.out.println("Updating " + this.coordinate[0] + ", " + this.coordinate[1] + " to " + coordinate[0] + ", " + coordinate[1]);

        assert (
                Math.abs(coordinate[0] - this.coordinate[0]) +
                Math.abs(coordinate[1] - this.coordinate[1])
        )== 1; // New coordinate must be of distance 1 from the previous coordinate

        if (next != null) {
            next.update(this.coordinate, addTail);
        } else if (addTail) {
            next = new SnakeNode(this.coordinate);
        }
        this.coordinate = coordinate;
    }


    /**
     * Add a new tail node to the snake
     * @param coordinate Coordinate of the new tail node.
     */
    protected void addTail(int[] coordinate) {
        assert coordinate != null;

        if (next == null) {
            next = new SnakeNode(coordinate);
        } else {
            next.addTail(coordinate);
        }
    }

    /**
     * Get the length of the snake
     * @return The length of the snake, including this node
     */
    public int length() {
        return 1 + (next != null ? next.length() : 0);
    }

    /**
     * Check if the snake contains a coordinate
     * @param coordinate Coordinate to check as an int[]
     * @return Whether the snake contains the coordinate
     */
    public boolean contains(int[] coordinate) {
        assert coordinate != null;

        if (Arrays.equals(this.coordinate, coordinate)) {
            return true;
        } else if (next != null) {
            return next.contains(coordinate);
        } else {
            return false;
        }
    }

    @Override
    public Iterator<SnakeNode> iterator() {
        return new SnakeIterator(this);
    }

    @Override
    public String toString() {
        return "[" + coordinate[0] + ", " + coordinate[1] + "]";
    }

    /**
     * Get a string representation of the snake
     * @return A string representation of the snake in the form of [x, y]<-[x, y]<-[x, y]...
     */
    public String snakeToString() {
        return toString() + (next != null ? "<-" + next.snakeToString() : "");
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SnakeNode newOther)) {
            return false;
        }
        return Arrays.equals(newOther.coordinate, coordinate);
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