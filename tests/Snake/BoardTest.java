package Snake;

import static org.junit.jupiter.api.Assertions.*;

import Snake.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {
    @Test
    @DisplayName("Generating a board with a width and height should create a correctly sized "
            + "board.")
    void testBoard() {
        Board board = new Board(10, 15);

        assertEquals(10, board.width);
        assertEquals(15, board.height);
        assertEquals(10, board.board.length);
        assertEquals(15, board.board[0].length);
    }

    @Test
    @DisplayName("Asking for the area of a board should return the correct area.")
    void testArea() {
        Board board = new Board(10, 15);

        assertEquals(150, board.area());
    }
}