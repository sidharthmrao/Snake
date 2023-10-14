package Snake.Utils;

import Snake.GameObjects.Board;
import Snake.Locations.Coordinate;
import Snake.GameObjects.SnakeNode;
import java.util.random.RandomGenerator;

public class Generator {
    private final RandomGenerator random = RandomGenerator.getDefault();

    /**
     * Generate a starting coordinate for the snake inside the board. Does not include edges.
     * The width and height of the board must be greater than 2.
     * @param board Snake.GameObjects.Board to generate inside
     * @return An int[] representing coordinate of the snake's head
     */
    public Coordinate genSnakeStart(Board board) {
        assert board != null;
        assert board.width > 2 && board.height > 2; // Snake.GameObjects.Board must be greater than 2x2

        // Generate a random coordinate inside the board, not at edges
        return new Coordinate(new int[] {
            random.nextInt(1, board.width - 1),
            random.nextInt(1, board.height - 1)
        });
    }


    /**
     * Generate a random coordinate for the food inside the board, but not inside the snake. The
     * snake must not cover the board.
     * @param board Snake.GameObjects.Board to generate inside
     * @param snake Snake.Snake to avoid generating inside
     * @return An int[] representing coordinate of the food
     */
    public Coordinate genFood(Board board, SnakeNode snake) {
        assert board != null;
        assert snake != null;
        assert snake.length() < board.area(); // Snake.Snake must not cover the board

        Coordinate food = new Coordinate(
                new int[] {random.nextInt(board.width), random.nextInt(board.height)}
        );
        while (snake.contains(food)) { // While the food is inside the snake, generate a new food
            food = new Coordinate(
                new int[] {random.nextInt(board.width), random.nextInt(board.height)}
            );
        }
        return food;
    }
}
