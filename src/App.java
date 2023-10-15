import java.awt.Dimension;
import javax.swing.*;
import Snake.Snake;

class App {

    private static void initWindow() {
        JFrame window = new JFrame("Snake");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int windowWidth = 1000;
        int windowHeight = 1000;
        window.setPreferredSize(new Dimension(windowWidth, windowHeight));

        int boardWidth = 50;
        int boardHeight = 50;

        Snake snake = new Snake(windowWidth, windowHeight, boardWidth, boardHeight);
        window.add(snake);
        window.addKeyListener(snake);

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::initWindow);
    }
}