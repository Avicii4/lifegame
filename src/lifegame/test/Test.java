package lifegame.test;

import lifegame.game.*;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Harry Chou on 2019/2/26.
 */
public class Test {
    private static final int BEGIN_WIDTH = 10;
    private static final int BEGIN_HEIGHT = 10;

    // max generation number in the game
    private static final int MAX_ROUNDS = 10;

    public static void main(String[] args) {
        LifeCell lifeCell = new LifeCell();
        LinkedList<Matrix> list = new LinkedList<>();
        Matrix[][] matrices = new Matrix[BEGIN_WIDTH][BEGIN_HEIGHT];
        for (int i = 0; i < matrices.length; i++) {
            for (int j = 0; j < matrices[i].length; j++) {
                matrices[i][j] = new Matrix();
                list.add(matrices[i][j]);
            }
        }

        int originalLifeNumber = (BEGIN_WIDTH * BEGIN_HEIGHT) / 4;
        Random random = new Random();
        // initialize the cells randomly
        while (originalLifeNumber >= 0) {
            int i = random.nextInt(list.size());
            Matrix m = list.remove(i);
            m.setAlive(true);
            originalLifeNumber--;
        }
        lifeCell.setCell(matrices);

        for (int i = 0; i < MAX_ROUNDS; i++) {
            lifeCell.setCell(matrices);
            matrices = lifeCell.getNextGeneration();
        }
    }
}
