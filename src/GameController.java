import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by  johnj on 10/31/16.
 */
public class GameController implements KeyListener, ActionListener {

    private final Grid grid;
    private final GameView gameView;
    private final Timer timer;

    private enum STATUS {RUNNING, PAUSED, GAMEOVER}

    private STATUS status;

    public GameController(Grid grid, GameView gameView) {
        this.grid = grid;
        this.gameView = gameView;
        this.timer = new Timer(Settings.MOVE_INTERVAL, this);
        this.status = STATUS.PAUSED;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                changeDirectionIfRunning(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                changeDirectionIfRunning(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                changeDirectionIfRunning(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                changeDirectionIfRunning(Direction.RIGHT);
                break;
            case KeyEvent.VK_ENTER:
                if (restartIfGameOver()) {
                    gameView.draw();
                }
                break;
            case KeyEvent.VK_SPACE:
                togglePause();
                break;
            case KeyEvent.VK_ESCAPE:
                timer.stop();
                Window window = SwingUtilities.getWindowAncestor(gameView.getCanvas());
                if (window != null) {
                    window.dispose();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (status != STATUS.RUNNING) {
            return;
        }

        if (grid.nextRound()) {
            gameView.draw();
        } else {
            timer.stop();
            status = STATUS.GAMEOVER;
            gameView.showGameOverMessage();
        }
    }

    private void changeDirectionIfRunning(Direction direction) {
        if (status == STATUS.RUNNING) {
            grid.changeDirection(direction);
        }
    }

    private boolean restartIfGameOver() {
        if (status != STATUS.GAMEOVER) {
            return false;
        }

        grid.restart();
        status = STATUS.PAUSED;
        return true;
    }

    private void togglePause() {
        if (status == STATUS.RUNNING) {
            status = STATUS.PAUSED;
            timer.stop();
        } else if (status == STATUS.PAUSED) {
            status = STATUS.RUNNING;
            timer.start();
        }
    }

}
