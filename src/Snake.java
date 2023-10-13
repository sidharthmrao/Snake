public class Snake {

    public static void main(String[] args) {
        Generator generator = new Generator();
    }
}

class Direction {
    int[] vector;

    public Direction(int[] vector) {
        this.vector = vector;
    }

    public static Direction UP = new Direction(new int[] {0, 1});
        public static Direction DOWN = new Direction(new int[] {0, -1});
        public static Direction LEFT = new Direction(new int[] {-1, 0});
        public static Direction RIGHT = new Direction(new int[] {1, 0});
}
