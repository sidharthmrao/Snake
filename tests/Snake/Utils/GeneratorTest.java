package Snake.Utils;

import static org.junit.jupiter.api.Assertions.*;

import Snake.GameObjects.Board;
import Snake.Locations.Coordinate;
import Snake.GameObjects.SnakeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GeneratorTest {
    @Test
    @DisplayName("Generating a new snake start should be inside the board, not touching edges.")
    void testGenSnakeStart() {
        Generator generator = new Generator();
        Board board = new Board(10, 10);
        for (int i = 0; i < 100; i++) {
            SnakeNode snake = new SnakeNode(generator.genSnakeStart(board));

            assertTrue(
                snake.coordinate().get(0) > 0 && snake.coordinate().get(0) < board.width() - 1,
                "Snake start should be inside the board."
            );
        }
    }

    @Test
    @DisplayName("Generating a new food should be inside the board, not inside the snake.")
    void testGenFood() {
        Generator generator = new Generator();
        Board board = new Board(10, 10);
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {5, 5}));

        for (int i = 0; i < 100; i++) {
            Coordinate food = generator.genFood(board, snake);

            assertNotEquals(snake.coordinate(), food, "Food should not be inside the snake.");
            assertTrue(food.get(0) >= 0 && food.get(0) <= board.width() - 1, "Food should be "
                    + "inside the board.");
            assertTrue(food.get(1) >= 0 && food.get(1) <= board.height() - 1, "Food should be "
                    + "inside the board.");
        }
    }
}