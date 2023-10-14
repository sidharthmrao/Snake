package Snake;

import Snake.GameObjects.Board;
import Snake.GameObjects.SnakeNode;
import Snake.Utils.Generator;
import Snake.Locations.Vector;

public class Snake {

    public static void main(String[] args) {
        Generator generator = new Generator();
        Board board = new Board(10, 10);
        SnakeNode snake = new SnakeNode(generator.genSnakeStart(board));
    }
}

record Direction(Vector vector) {
    final public static Direction UP = new Direction(new Vector(new int[]{0, 1}));
    final public static Direction DOWN = new Direction(new Vector(new int[]{0, -1}));
    final public static Direction LEFT = new Direction(new Vector(new int[]{-1, 0}));
    final public static Direction RIGHT = new Direction(new Vector(new int[]{1, 0}));
}
