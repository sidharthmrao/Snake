public class Board {

    int width;
    int height;
    int[][] board;

    /**
     * Create a new board with a width and height
     * @param width Width of the board
     * @param height Height of the board
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    /**
     * Return the area of the board
     * @return Area of the board
     */
    public int area() {
        return width * height;
    }
}


