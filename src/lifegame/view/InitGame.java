package lifegame.view;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by Harry Chou on 2019/2/25.
 */
public class InitGame extends MouseAdapter implements MouseMotionListener {
    private GameView gameView;

    void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     * Use mice to initialize cells' life.
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        if (gameView.generationNumber == 0) {
            if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
                // left click to set a cell alive
                setLife(mouseEvent.getX(), mouseEvent.getY(), true);
            } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                // right click to cancel
                setLife(mouseEvent.getX(), mouseEvent.getY(), false);
            }
        }
    }

    private void setLife(int x, int y, boolean lifeState) {
        gameView.cycleNumber = -1;
        for (int i = 0; i < gameView.cell.length; i++) {
            for (int j = 0; j < gameView.cell[i].length; j++) {
                int cx = gameView.cell[i][j].getX();
                int cy = gameView.cell[i][j].getY();
                Rectangle rectangle = new Rectangle(cx, cy, gameView.width, gameView.height);
                if (rectangle.contains(x, y)) {
                    gameView.cell[i][j].setAlive(lifeState);
                }
            }
            gameView.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if (gameView.generationNumber == 0) {
            setLife(mouseEvent.getX(), mouseEvent.getY(), true);
        }
    }
}
