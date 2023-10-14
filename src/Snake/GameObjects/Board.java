package Snake.GameObjects;

import Snake.Locations.Coordinate;

public class Board {

    public final int width;
    public final int height;
    public final Coordinate[][] board;

    /**
     * Create a new board with a width and height
     * @param width Width of the board
     * @param height Height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new Coordinate[width][height];
    }

    /**
     * Return the area of the board
     * @return Area of the board
     */
    public int area() {
        return width * height;
    }
}


