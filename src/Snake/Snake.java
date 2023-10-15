package Snake;

import Snake.GameObjects.Board;
import Snake.GameObjects.SnakeNode;
import Snake.Locations.Coordinate;
import Snake.Utils.Generator;
import Snake.Locations.Vector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
    private final int fps;
    private final double delay;
    private final int scale;
    private final boolean loop; // When the snake hits edge, loop to other side.

    /** Game Objects */
    private final Board board;
    private final SnakeNode snake;
    private Coordinate food;

    /** Game State */
    private Direction direction = Direction.NONE; // Direction of the snake
    private Direction lastDirection = Direction.NONE; // Last moved direction of the snake

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
    public Snake(int windowWidth, int windowHeight, int boardWidth, int boardHeight, int fps, boolean loop) {
        canvas = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);

        this.fps = fps;
        this.delay = 1000 / (double) fps;
        this.loop = loop;
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
            if (lastDirection == Direction.DOWN) {
                return;
            }
            direction = Direction.UP;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (lastDirection == Direction.UP) {
                    return;
            }
            direction = Direction.DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (lastDirection == Direction.RIGHT) {
                return;
            }
            direction = Direction.LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (lastDirection == Direction.LEFT) {
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

            lastDirection = direction;

            if (snake.containsSelf()) {
                System.out.println("Game Over");
                System.exit(0);
            }

            if (!board.checkEdge(snake.coordinate())) {
                if (loop) {
                    Coordinate current = snake.coordinate();
                    int nextX;
                    if (current.get(0) < 0) {
                        nextX = board.width - 1;
                    } else if (current.get(0) > board.width - 1) {
                        nextX = 0;
                    } else {
                        nextX = current.get(0);
                    }

                    int nextY;
                    if (current.get(1) < 0) {
                        nextY = board.height - 1;
                    } else if (current.get(1) > board.height - 1) {
                        nextY = 0;
                    } else {
                        nextY = current.get(1);
                    }

                    System.out.println(nextX + " " + nextY);

                    snake.offsetUpdate(new Coordinate(nextX, nextY));
                } else {
                    System.out.println("Game Over");
                    System.exit(0);
                }
            }

            drawBoard(board);
            drawFood(food);
            drawSnake(snake);
            drawText("Score: " + snake.length(), new Coordinate(0, 0), 100, 50);

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

    public void drawText(String text, Coordinate coordinate, int width, int height) {
        Graphics2D g2d = (Graphics2D) canvas.getGraphics();
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Blinker", Font.BOLD, 25));

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        Rectangle rect = new Rectangle(coordinate.get(0), coordinate.get(1), width, height);

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.drawString(text, x, y);
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
