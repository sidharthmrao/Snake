package Snake.GameObjects;

import static org.junit.jupiter.api.Assertions.*;

import Snake.GameObjects.SnakeNode;
import Snake.Locations.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SnakeNodeTest {

    @Test
    @DisplayName("Asking for the length of a snake with only a head should return 1")
    void testNoLength() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));

        assertEquals(1, snake.length(), "Snake should have a length of 1");
    }

    @Test
    @DisplayName("Asking for the length of a snake with a head and a tail should return 2")
    void testAddTail() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake.addTail(new Coordinate(new int[] {0, 2}));

        assertEquals(2, snake.length(), "Snake should have a length of 2");
    }

    @Test
    @DisplayName("Asking for the coordinates of a snake with an updated tail should return "
            + "correctly.")
    void testUpdate() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake.addTail(new Coordinate(new int[] {0, 2}));
        snake.update(new Coordinate(new int[] {0, 0}), false);

        assertEquals("[0, 0]<-[0, 1]", snake.snakeToString(), "Snake should be updated correctly.");
    }

    @Test
    @DisplayName("Asking for the coordinates of a snake with an updated and added tail should "
            + "return correctly.")
    void testUpdateTail() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake.addTail(new Coordinate(new int[] {0, 2}));
        snake.update(new Coordinate(new int[] {0, 0}), true);

        assertEquals("[0, 0]<-[0, 1]<-[0, 2]", snake.snakeToString(), "Snake should be updated correctly.");
    }

    @Test
    @DisplayName("Asking if the snake contains a contained coordinate should return true")
    void testContains() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake.addTail(new Coordinate(new int[] {0, 2}));
        snake.addTail(new Coordinate(new int[] {0, 3}));

        assertTrue(snake.contains(new Coordinate(new int[] {0, 1})), "Snake should contain [0, 1]");
    }

    @Test
    @DisplayName("Asking if the snake contains a not contained coordinate should return false")
    void testNotContains() {
        SnakeNode snake = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake.addTail(new Coordinate(new int[] {0, 2}));

        assertFalse(snake.contains(new Coordinate(new int[] {0, 3})),
                "Snake should not contain [0, 3]");
    }

    @Test
    @DisplayName("Asking if two equal snakes are equal with just a head should return true")
    void testEqualsLengthOne() {
        SnakeNode snake1 = new SnakeNode(new Coordinate(new int[] {0, 1}));
        SnakeNode snake2 = new SnakeNode(new Coordinate(new int[] {0, 1}));

        assertEquals(snake1, snake2, "Snake should be equal to itself");
    }

    @Test
    @DisplayName("Asking if two equal snakes are equal with a head and a tail should return true")
    void testEqualsLengthTwo() {
        SnakeNode snake1 = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake1.addTail(new Coordinate(new int[] {0, 2}));
        SnakeNode snake2 = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake2.addTail(new Coordinate(new int[] {0, 2}));

        assertEquals(snake1, snake2, "Snake should be equal to itself");
    }

    @Test
    @DisplayName("Asking if two unequal snakes are equal with same length should return false")
    void testNotEqualsLengthOne() {
        SnakeNode snake1 = new SnakeNode(new Coordinate(new int[] {0, 1}));
        SnakeNode snake2 = new SnakeNode(new Coordinate(new int[] {0, 2}));

        assertNotEquals(snake1, snake2, "Snake should not be equal to itself");
    }

    @Test
    @DisplayName("Asking if two unequal snakes are equal with different length should return false")
    void testNotEqualsDiffLength() {
        SnakeNode snake1 = new SnakeNode(new Coordinate(new int[] {0, 1}));
        snake1.addTail(new Coordinate(new int[] {0, 2}));
        SnakeNode snake2 = new SnakeNode(new Coordinate(new int[] {0, 1}));

        assertNotEquals(snake1, snake2, "Snake should not be equal to itself");
    }
}