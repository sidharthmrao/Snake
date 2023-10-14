package Snake;

import static org.junit.jupiter.api.Assertions.*;

import Snake.SnakeNode;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SnakeNodeTest {

    @Test
    @DisplayName("Asking for the length of a snake with only a head should return 1")
    void testNoLength() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});

        assertEquals(1, snake.length());
    }

    @Test
    @DisplayName("Asking for the length of a snake with a head and a tail should return 2")
    void testAddTail() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});

        assertEquals(2, snake.length());
    }

    @Test
    @DisplayName("Asking for the coordinates of a snake with an updated tail should return "
            + "correctly.")
    void testUpdate() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});
        snake.update(new int[] {0, 0}, false);

        ArrayList<int[]> vals = new ArrayList<>();

        for (SnakeNode node : snake) {
            vals.add(node.coordinate());
        }

        assertEquals(1, vals.get(1)[1]);
    }

    @Test
    @DisplayName("Asking for the coordinates of a snake with an updated and added tail should "
            + "return correctly.")
    void testUpdateTail() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});
        snake.update(new int[] {0, 0}, true);

        ArrayList<int[]> vals = new ArrayList<>();

        for (SnakeNode node : snake) {
            vals.add(node.coordinate());
        }

        assertEquals(3, vals.size());
        assertEquals(2, vals.get(2)[1]);
    }

    @Test
    @DisplayName("Asking if the snake contains a contained coordinate should return true")
    void testContains() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});
        snake.addTail(new int[] {0, 3});

        assertTrue(snake.contains(new int[] {0, 1}));
    }

    @Test
    @DisplayName("Asking if the snake contains a not contained coordinate should return false")
    void testNotContains() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});

        assertFalse(snake.contains(new int[] {0, 3}));
    }
}