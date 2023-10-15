package Snake;

import Snake.GameObjects.Board;
import Snake.GameObjects.SnakeNode;
import Snake.Locations.Coordinate;
import Snake.Utils.Generator;
import Snake.Locations.Vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {
    private final BufferedImage canvas;

    /** Render Vars */
    private final int fps = 10;
    private final double delay = 1000 / (double) fps;
    private final int scale;

    /** Game Objects */
    private final Board board;
    private final SnakeNode snake;
    private Coordinate food;

    /** Game State */
    private Direction direction = Direction.NONE;

    /** Utils */
    private final Generator generator;
    private final Timer timer;

    /**
     * Create a new Snake panel. This will control the drawing of the game.
     * @param windowWidth Width of the window
     * @param windowHeight Height of the window
     * @param boardWidth Width of the board
     * @param boardHeight Height of the board
     */
    public Snake(int windowWidth, int windowHeight, int boardWidth, int boardHeight) {
        canvas = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);
        this.scale = windowWidth / boardWidth;

        this.generator = new Generator();
        this.board = new Board(boardWidth, boardHeight);
        this.snake = new SnakeNode(generator.genSnakeStart(board));
        this.food = generator.genFood(board, snake);

        this.timer = new Timer((int) delay, this);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Update the direction of the snake based on the key pressed. Will not allow updating in the
     * opposite direction of the current direction.
     * @param e KeyEvent to check
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (direction == Direction.DOWN) {
                return;
            }
            direction = Direction.UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (direction == Direction.UP) {
                    return;
            }
            direction = Direction.DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (direction == Direction.RIGHT) {
                return;
            }
            direction = Direction.LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (direction == Direction.LEFT) {
                return;
            }
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            if (snake.contains(food)) {
                food = generator.genFood(board, snake);
                snake.update(direction.vector(), true);
            } else {
                snake.update(direction.vector(), false);
            }

            if (snake.containsSelf()) {
                System.out.println("Game Over");
                System.exit(0);
            }

            if (!board.checkEdge(snake.coordinate())) {
                System.out.println("Game Over");
                System.exit(0);
            }

            drawBoard(board);
            drawFood(food);
            drawSnake(snake);

            repaint();
        }
    }

    /**
     * Paint the canvas
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    /**
     * Draw a rectangle on the canvas
     * @param c Color of the rectangle
     * @param x1 x coordinate of the top left corner
     * @param y1 y coordinate of the top left corner
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
    public void drawRect(Color c, int x1, int y1, int width, int height) {
        int color = c.getRGB();
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1; y < y1 + height; y++) {
                canvas.setRGB(x, y, color);
            }
        }
    }

    /**
     * Draw the board
     * @param board Board to draw
     */
    public void drawBoard(Board board) {
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                drawRect(Color.BLACK, x * scale, y * scale, scale, scale);
            }
        }
    }

    /**
     * Draw food on the board
     * @param food Coordinate of the food
     */
    public void drawFood(Coordinate food) {
        drawRect(Color.RED, food.get(0) * scale, food.get(1) * scale, scale, scale);
    }

    /**
     * Draw a snake on the board
     * @param snake Snake to draw
     */
    public void drawSnake(SnakeNode snake) {
        for (SnakeNode snakeNode : snake) {
            Coordinate coordinate = snakeNode.coordinate();
            drawRect(Color.GREEN, coordinate.get(0) * scale, coordinate.get(1) * scale, scale,
                    scale);
        }
    }
}

record Direction(Vector vector) {
    final public static Direction UP = new Direction(new Vector(new int[]{0, -1}));
    final public static Direction DOWN = new Direction(new Vector(new int[]{0, 1}));
    final public static Direction LEFT = new Direction(new Vector(new int[]{-1, 0}));
    final public static Direction RIGHT = new Direction(new Vector(new int[]{1, 0}));

    final public static Direction NONE = new Direction(new Vector(new int[]{0, 0}));
}
