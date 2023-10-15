package Snake.Locations;

import java.util.Arrays;

public class Coordinate {

    /**
     * Coordinate. Must be of length 2.
     */
    int[] coordinate;

    /**
     * Create a new Coordinate (x,y) with an array of length 2.
     * @param coordinate Coordinate of the Coordinate as an int[]. Must be of length 2.
     */
    public Coordinate(int[] coordinate) {
        assert coordinate != null && coordinate.length == 2;

        this.coordinate = coordinate;
    }

    public Coordinate(int x, int y) {
        this(new int[] {x, y});
    }

    /**
     * Get the coordinate as an array of length 2.
     * @return An int[] representing the coordinate.
     */
    public int[] coordinate() {
        return coordinate;
    }

    /**
     * Update the coordinate.
     * @param coordinate New coordinate. Must be of length 2.
     */
    public void update(int[] coordinate) {
        assert coordinate != null && coordinate.length == 2;

        this.coordinate = coordinate;
    }

    /**
     * Get the coordinate at an index.
     * @param index Index of the coordinate to get.
     * @return An int representing the coordinate at the index. Must be 0 or 1.
     */
    public int get(int index) {
        assert index == 0 || index == 1;

        return coordinate[index];
    }

    @Override
    public String toString() {
            return "[" + coordinate[0] + ", " + coordinate[1] + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Coordinate c = (Coordinate) other;
        return Arrays.equals(coordinate, c.coordinate);
    }
}

