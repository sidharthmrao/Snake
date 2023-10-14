package Snake;

import static org.junit.jupiter.api.Assertions.*;

import Snake.Board;
import Snake.Generator;
import Snake.SnakeNode;
import java.util.Arrays;
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

                assertTrue(snake.coordinate()[0] > 0 && snake.coordinate()[0] < board.width - 1);
            }
    }

    @Test
    @DisplayName("Generating a new food should be inside the board, not inside the snake.")
    void testGenFood() {
        Generator generator = new Generator();
        Board board = new Board(10, 10);
        SnakeNode snake = new SnakeNode(new int[] {5, 5});

        for (int i = 0; i < 100; i++) {
            int[] food = generator.genFood(board, snake);

            assertFalse(Arrays.equals(food, snake.coordinate()));
            assertTrue(food[0] >= 0 && food[0] <= board.width - 1);
            assertTrue(food[1] >= 0 && food[1] <= board.height - 1);
        }
    }
}