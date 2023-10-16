package Snake.GameObjects;

import Snake.Locations.Coordinate;
import Snake.Locations.Vector;
import java.util.Iterator;

public class SnakeNode implements Iterable<SnakeNode> {

    SnakeNode next;
    private Coordinate coordinate;

    /**
     * Create a new snake node with a coordinate. Takes the form of the head of a linked list.
     *
     * @param coordinate Coordinate of the snake node as a Coordinate.
     */
    public SnakeNode(Coordinate coordinate) {
        assert coordinate != null;

        this.coordinate = coordinate;
        this.next = null;
    }

    /**
     * Get the next node in the snake
     *
     * @return The next node in the snake. Null if this is the tail.
     */
    public SnakeNode next() {
        return next;
    }

    /**
     * Get the coordinate of the snake node
     *
     * @return A Coordinate representing the coordinate of the snake node as [x, y]
     */
    public Coordinate coordinate() {
        return coordinate;
    }

    /**
     * Update the entire snake from this node to the tail with a new Coordinate. If addTail is true,
     * add a new tail node. Every node after this one will be updated with the previous node's
     * coordinate, and the new coordinate will be this node's coordinate.
     *
     * @param coordinate New coordinate.
     * @param addTail    Whether to add a new tail node
     */
    public void update(Coordinate coordinate, boolean addTail) {
        assert coordinate != null;

        if (next != null) {
            next.update(this.coordinate, addTail);
        } else if (addTail) {
            next = new SnakeNode(this.coordinate);
        }
        this.coordinate = coordinate;
    }

    /**
     * Update the entire snake from this node to the tail with a new Vector. If addTail is true, add
     * a new tail node. Every node after this one will be updated with the previous node's
     * coordinate, and the new coordinate will be this node's coordinate.
     *
     * @param vector  Vector to add to the coordinate. Must be of length 2.
     * @param addTail Whether to add a new tail node
     */
    public void update(Vector vector, boolean addTail) {
        assert vector != null;

        update(vector.add(coordinate), addTail);
    }

    /**
     * Update only the current node. Used to teleport the snake from one location to another.
     *
     * @param coordinate Coordinate to offset snake to.
     */
    public void offsetUpdate(Coordinate coordinate) {
        assert coordinate != null;

        this.coordinate = coordinate;
    }

    /**
     * Add a new tail node to the snake.
     *
     * @param coordinate Coordinate of the new tail node. Must be of length 2.
     */
    protected void addTail(Coordinate coordinate) {
        assert coordinate != null;

        if (next == null) {
            next = new SnakeNode(coordinate);
        } else {
            next.addTail(coordinate);
        }
    }

    /**
     * Get the length of the snake.
     *
     * @return The length of the snake, including this node
     */
    public int length() {
        return 1 + (next != null ? next.length() : 0);
    }

    /**
     * Check if the snake contains a provided Coordinate
     *
     * @param coordinate Coordinate to check as an int[] of length 2
     * @return Whether the snake contains the Coordinate
     */
    public boolean contains(Coordinate coordinate) {
        assert coordinate != null;

        if (this.coordinate.equals(coordinate)) {
            return true;
        } else if (next != null) {
            return next.contains(coordinate);
        } else {
            return false;
        }
    }

    /**
     * Check if the snake contains itself. Used to check if the snake has collided with itself.
     *
     * @return Whether the snake contains itself (has collided with itself)
     */
    public boolean containsSelf() {
        if (next == null) {
            return false;
        }
        return next.contains(coordinate);
    }

    @Override
    public Iterator<SnakeNode> iterator() {
        return new SnakeIterator(this);
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }

    /**
     * Get a string representation of the snake
     *
     * @return A string representation of the snake in the form of [x, y]<-[x, y]<-[x, y]...
     */
    public String snakeToString() {
        return this + (next != null ? "<-" + next.snakeToString() : "");
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SnakeNode newOther)) {
            return false;
        }

        if (!coordinate.equals(newOther.coordinate)) {
            return false;
        }

        if (next == null ^ newOther.next() == null) {
            return false;
        } else if (next == null) {
            return true;
        }

        return next.equals(newOther.next());
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
}