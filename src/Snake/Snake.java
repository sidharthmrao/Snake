package Snake;

public class Snake {

    public static void main(String[] args) {
        Generator generator = new Generator();
        Board board = new Board(10, 10);
        SnakeNode snake = new SnakeNode(generator.genSnakeStart(board));
    }
}

record Direction(int[] vector) {
    final public static Direction UP = new Direction(new int[]{0, 1});
    final public static Direction DOWN = new Direction(new int[]{0, -1});
    final public static Direction LEFT = new Direction(new int[]{-1, 0});
    final public static Direction RIGHT = new Direction(new int[]{1, 0});
}
