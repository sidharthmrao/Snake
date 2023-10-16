package Snake.GameObjects;
import Snake.Locations.Coordinate;

public record Board(int width, int height) {

    /**
     * Create a new board with a width and height
     *
     * @param width  Width of the board
     * @param height Height of the board
     */
    public Board {}

    /**
     * Get the width of the board
     *
     * @return Return the width of the board as an int
     */
    @Override
    public int width() {
        return width;
    }

    /**
     * Get the height of the board
     *
     * @return Return the height of the board as an int
     */
    @Override
    public int height() {
        return height;
    }


    /**
     * Return the area of the board
     *
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
            coordinate.x() >= 0 &&
            coordinate.x() < width &&
            coordinate.y() >= 0 &&
            coordinate.y() < height
        );
    }
}


