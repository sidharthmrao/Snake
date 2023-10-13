import java.util.random.RandomGenerator;

public class Generator {
    RandomGenerator random = RandomGenerator.getDefault();

    /**
     * Generate a starting coordinate for the snake inside the board. Does not include edges.
     * The width and height of the board must be greater than 2.
     * @param board Board to generate inside
     * @return An int[] representing coordinate of the snake's head
     */
    public int[] genSnakeStart(Board board) {
        assert board.width > 2 && board.height > 2; // Board must be greater than 2x2

        return new int[] { // Generate a random coordinate inside the board, not at edges
                random.nextInt(1, board.width - 1),
                random.nextInt(1, board.height - 1)
        };
    }


    /**
     * Generate a random coordinate for the food inside the board, but not inside the snake. The
     * snake must not cover the board.
     * @param board Board to generate inside
     * @param snake Snake to avoid generating inside
     * @return An int[] representing coordinate of the food
     */
    public int[] genFood(Board board, SnakeNode snake) {
        assert snake.length() < board.area(); // Snake must not cover the board

        int[] food = new int[] {random.nextInt(board.width), random.nextInt(board.height)};
        while (snake.contains(food)) { // While the food is inside the snake, generate a new food
            food = new int[] {random.nextInt(board.width), random.nextInt(board.height)};
        }
        return food;
    }
}
