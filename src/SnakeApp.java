import javax.swing.*;
import java.awt.*;

/**
 * Created by  johnj on 10/31/16.
 */
public class SnakeApp {


    public void init() {

        Grid grid = new Grid(Settings.GRID_WIDTH / Settings.NODE_SIZE,
                             Settings.GRID_HEIGHT / Settings.NODE_SIZE);

        JFrame window = new JFrame("贪吃蛇");

        //画出棋盘和蛇
        GameView gameView = new GameView(grid);
        gameView.init();
        gameView.getCanvas().setPreferredSize(new Dimension(Settings.GRID_WIDTH, Settings.GRID_HEIGHT));
        window.getContentPane().add(gameView.getCanvas(), BorderLayout.CENTER);

        GameController gameController = new GameController(grid, gameView);

        window.pack();
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(gameController);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeApp app = new SnakeApp();
            app.init();
        });
    }

}
