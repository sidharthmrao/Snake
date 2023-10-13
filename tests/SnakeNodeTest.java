import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import org.junit.jupiter.api.Test;

class SnakeNodeTest {

    @Test
    void testNoLength() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        assertEquals(1, snake.length());
    }

    @Test
    void testAddTail() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});
        assertEquals(2, snake.length());
    }

    @Test
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

        System.out.println(snake.snakeToString());
    }

    @Test
    void testContains() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});
        snake.addTail(new int[] {0, 3});

        assertTrue(snake.contains(new int[] {0, 1}));
    }

        @Test
    void testNotContains() {
        SnakeNode snake = new SnakeNode(new int[] {0, 1});
        snake.addTail(new int[] {0, 2});

        assertFalse(snake.contains(new int[] {0, 3}));
    }
}