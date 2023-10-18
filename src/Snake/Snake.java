package Snake;

import Snake.GameObjects.Board;
import Snake.GameObjects.SnakeNode;
import Snake.Locations.Coordinate;
import Snake.Locations.Vector;
import Snake.Utils.Generator;
import java.awt.Color;
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
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {
    private final BufferedImage canvas;
    private final int scale;
    /**
     * Game Objects
     */
    private final Board board;
    private SnakeNode snake;
    private Coordinate food;
    /**
     * Utils
     */
    private final Generator generator;
    private Timer timer;
    private boolean loop; // When the snake hits edge, loop to other side.
    /**
     * Game State
     */
    private Direction direction = Direction.NONE; // Direction of the snake
    private Direction lastDirection = Direction.NONE; // Last moved direction of the snake
    private boolean gameRunning;
    private String gameMode; // Classic, Walls, Portals, etc.
    private int speed = 2;
    private double delay;
    private HashMap<String, Integer> highScores = new HashMap<>();
    private double startTime;

    /**
     * Create a new Snake panel. This will control the drawing of the game.
     *
     * @param windowWidth  Width of the window
     * @param windowHeight Height of the window
     * @param boardWidth   Width of the board
     * @param boardHeight  Height of the board
     */
    public Snake(int windowWidth, int windowHeight, int boardWidth, int boardHeight, int fps,
            boolean loop) {
        canvas = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);

        this.delay = 500 / (double) fps;
        this.loop = loop;
        this.scale = windowWidth / boardWidth;

        this.generator = new Generator();
        this.board = new Board(boardWidth, boardHeight);

        highScores.put("Classic", 0);
        highScores.put("Walls", 0);
        highScores.put("Portals", 0);
        gameMode = "Classic";

        reset();
    }

    /**
     * Restart the game. Resets the snake object and direction.
     */
    public void reset() {
        this.timer = new Timer((int) delay * (4 - speed), this);
        this.snake = new SnakeNode(generator.genSnakeStart(board));
        this.food = generator.genFood(board, snake);
        direction = Direction.NONE;
        lastDirection = Direction.NONE;

        gameRunning = true;
        timer.start();
        startTime = -1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Update the direction of the snake based on the key pressed. Will not allow updating in the
     * opposite direction of the current direction.
     *
     * @param e KeyEvent to check
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameRunning) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();
                }
                if (lastDirection == Direction.DOWN) {
                    return;
                }
                direction = Direction.UP;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();
                }
                if (lastDirection == Direction.UP) {
                    return;
                }
                direction = Direction.DOWN;
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();
                }
                if (lastDirection == Direction.RIGHT) {
                    return;
                }
                direction = Direction.LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();
                }
                if (lastDirection == Direction.LEFT) {
                    return;
                }
                direction = Direction.RIGHT;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && !gameRunning) {
                reset();
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                loop = !loop;
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                speed = speed == 2 ? 3 : speed == 3 ? 1 : 2;
                timer.setDelay((int) delay * speed);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void classicSnakeUpdate() {
        if (snake.contains(food)) {
            food = generator.genFood(board, snake);
            snake.update(direction.vector(), true);
        } else {
            snake.update(direction.vector(), false);
        }

        lastDirection = direction;

        if (snake.containsSelf()) {
            gameRunning = false;
        }

        if (!board.checkEdge(snake.coordinate())) {
            if (loop) {
                Coordinate current = snake.coordinate();
                int nextX;
                if (current.x() < 0) {
                    nextX = board.width() - 1;
                } else if (current.x() > board.width() - 1) {
                    nextX = 0;
                } else {
                    nextX = current.x();
                }

                int nextY;
                if (current.y() < 0) {
                    nextY = board.height() - 1;
                } else if (current.y() > board.height() - 1) {
                    nextY = 0;
                } else {
                    nextY = current.y();
                }

                snake.offsetUpdate(new Coordinate(nextX, nextY));
            } else {
                gameRunning = false;
            }
        }
    }

    public void drawGame() {
        drawBoard(board);
        drawFood(food);
        drawSnake(snake);

        int highScore = highScores.get(gameMode);

        drawText("Score: " + snake.length(), new Coordinate(10, 24), 25,
                snake.length() < highScore ? Color.RED : Color.GREEN, false);
        drawText("High Score: " + highScore, new Coordinate(10, 46), 25,
                Color.GREEN, false);
        drawText("Mode: " + gameMode, new Coordinate(10, 70), 25,
                Color.GREEN, false);
        drawText("Time Elapsed: " + (startTime == -1 ? "0s" :
                        (System.currentTimeMillis() - startTime) / 1000 + "s"),
                new Coordinate(10, 94), 25, Color.GREEN, false);
    }

    public void drawGameOver() {
        Coordinate initial = new Coordinate(board.width() * scale / 2, 8 * getHeight() / 20);

        drawText("GAME OVER.", initial, 50, Color.GREEN, true);
        drawText("PRESS ENTER TO RESTART.",
                new Vector(0, 30).add(initial),30, Color.GREEN, true);
        drawSettings(new Vector(0, 100).add(initial));
    }

    public void drawSettings(Coordinate initial) {
        Coordinate optionsStart = new Vector(-100, 0).add(initial);
        drawText("OPTIONS",
                 optionsStart,
                15,
                 Color.WHITE,
                true);

        clearText("Edge Looping (L): OFF",
                 new Vector(0, 30).add(optionsStart),
                15,
                true);

        drawText("Edge Looping (L): " + (loop ? "ON" : "OFF"),
                 new Vector(0, 30).add(optionsStart),
                15,
                 loop ? Color.GREEN : Color.RED,
                true);

        clearText("Speed (S): MEDIUM",
                 new Vector(0, 50).add(optionsStart),
                15,
                true);

        drawText("Speed (S): " + (speed == 2 ? "Medium" : speed == 3 ? "Fast" : "Slow"),
                 new Vector(0, 50).add(optionsStart),
                15,
                 Color.GREEN,
                true);
    }

    /**
     * Update the game state
     *
     * @param e ActionEvent to check
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            if (gameRunning) {
                switch (gameMode) {
                    default -> classicSnakeUpdate();
                }

                if (gameRunning) {
                    drawGame();
                }
            }

            if (!gameRunning) {
                drawGameOver();
            }

            if (snake.length() > highScores.get(gameMode)) {
                highScores.put(gameMode, snake.length());
            }
            repaint();
        }
    }

    /**
     * Paint the canvas
     *
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void clearText(String text, Coordinate coordinate, int fontSize, boolean center) {
        drawText(new String(new char[text.length()]).replace("\0", "█"), coordinate, fontSize, Color.BLACK
                , center);
    }

    /**
     * Draw text on the canvas
     *
     * @param text       Text to draw
     * @param coordinate Coordinate of the text
     * @param fontSize   Font size of the text
     * @param color      Color of the text
     * @param center     Whether to center the text on the x-axis
     */
    public void drawText(String text, Coordinate coordinate, int fontSize,
            Color color, boolean center) {
        Graphics2D g2d = (Graphics2D) canvas.getGraphics();

        g2d.setColor(color);
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));

        if (center) {
            FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

            int x = coordinate.x() - metrics.stringWidth(text) / 2;
            g2d.drawString(text, x, coordinate.y());
        } else {
            g2d.drawString(text, coordinate.x(), coordinate.y());
        }
    }

    /**
     * Draw a rectangle on the canvas
     *
     * @param c      Color of the rectangle
     * @param x1     x coordinate of the top left corner
     * @param y1     y coordinate of the top left corner
     * @param width  Width of the rectangle
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
     *
     * @param board Board to draw
     */
    public void drawBoard(Board board) {
        for (int x = 0; x < board.width(); x++) {
            for (int y = 0; y < board.height(); y++) {
                drawRect(Color.BLACK, x * scale, y * scale, scale, scale);
            }
        }
    }

    /**
     * Draw food on the board
     *
     * @param food Coordinate of the food
     */
    public void drawFood(Coordinate food) {
        drawRect(Color.RED, food.x() * scale, food.y() * scale, scale, scale);
    }

    /**
     * Draw a snake on the board
     *
     * @param snake Snake to draw
     */
    public void drawSnake(SnakeNode snake) {
        for (SnakeNode snakeNode : snake) {
            Coordinate coordinate = snakeNode.coordinate();
            drawRect(Color.GREEN, coordinate.x() * scale, coordinate.y() * scale, scale, scale);
        }
    }
}

/**
 * Direction to move the snake in
 */
record Direction(Vector vector) {

    final public static Direction UP = new Direction(new Vector(0, -1));
    final public static Direction DOWN = new Direction(new Vector(0, 1));
    final public static Direction LEFT = new Direction(new Vector(-1, 0));
    final public static Direction RIGHT = new Direction(new Vector(1, 0));
    final public static Direction NONE = new Direction(new Vector(0, 0));
}
