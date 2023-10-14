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
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel {
    private final BufferedImage canvas;
    int scale;

    public Snake(int width, int height, int scale) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.scale = scale;
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }

    public void drawRect(Color c, int x1, int y1, int width, int height) {
        int color = c.getRGB();
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1; y < y1 + height; y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawBoard(Board board) {
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                drawRect(Color.BLACK, x * scale, y * scale, scale, scale);
            }
        }
    }

    public void drawFood(Coordinate food) {
        drawRect(Color.RED, food.get(0) * scale, food.get(1) * scale, scale, scale);
    }

    public void drawSnake(SnakeNode snake) {
        for (SnakeNode snakeNode : snake) {
            Coordinate coordinate = snakeNode.coordinate();
            drawRect(Color.GREEN, coordinate.get(0) * scale, coordinate.get(1) * scale, scale,
                    scale);
        }
    }

    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;

        Generator generator = new Generator();
        Board board = new Board(50, 50);
        SnakeNode snake = new SnakeNode(generator.genSnakeStart(board));
        Coordinate food = generator.genFood(board, snake);


        JFrame frame = new JFrame("Snake");
        Snake snakePanel = new Snake(width, height, width / board.width);

        frame.add(snakePanel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        snakePanel.drawBoard(board);
        snakePanel.drawFood(food);
        snakePanel.drawSnake(snake);
        snakePanel.repaint();
    }
}

record Direction(Vector vector) {
    final public static Direction UP = new Direction(new Vector(new int[]{0, 1}));
    final public static Direction DOWN = new Direction(new Vector(new int[]{0, -1}));
    final public static Direction LEFT = new Direction(new Vector(new int[]{-1, 0}));
    final public static Direction RIGHT = new Direction(new Vector(new int[]{1, 0}));
}
