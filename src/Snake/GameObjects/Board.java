package Snake.GameObjects;

import Snake.Locations.Coordinate;

public class Board {

    public final int width;
    public final int height;

    /**
     * Create a new board with a width and height
     * @param width Width of the board
     * @param height Height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Return the area of the board
     * @return Area of the board
     */
    public int area() {
        return width * height;
    }

    /**
     * Check if a coordinate is on the board
     *
     * @param coordinate Coordinate to check
     * @return Whether the coordinate is on the board
     */
    public boolean checkEdge(Coordinate coordinate) {
        assert coordinate != null;

        return (
            coordinate.get(0) >= 0 &&
            coordinate.get(0) < width &&
            coordinate.get(1) >= 0 &&
            coordinate.get(1) < height
        );
    }
}


