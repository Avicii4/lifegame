package lifegame.game;

import lifegame.view.GameView;

import java.util.*;

/**
 * Created by Harry Chou on 2019/2/25.
 */
public class LifeCell {
    public Matrix[][] cell; // define each cell
    private Set<Matrix> cellSet = new HashSet<>(); //define the surrounding 8 cells

    public void setCell(Matrix[][] cell) {
        this.cell = cell;
    }

    /**
     * get the next generation of matrix
     */
    public Matrix[][] getNextGeneration() {
        // get a copy of the old matrix
        Matrix[][] newCellSet = new GameView().copyCell(cell);
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                int countAliveCell = aliveCellCounter(i, j, cell);
                if (countAliveCell == 3) {
                    cell[i][j].setAlive(true);
                } else if (countAliveCell == 2) {
                    cell[i][j].setAlive(cell[i][j].getAlive());
                } else {
                    cell[i][j].setAlive(false);
                }
            }
        }
        return cell;
    }

    /**
     * get the number of living cells around each cell
     */
    private int aliveCellCounter(int i, int j, Matrix[][] cell) {
        int counter = 0;
        cellSet.clear();
        if (i > 1) { // up
            cellSet.add(cell[i - 1][j]);
        }
        if (i < cell.length - 1) { //down
            cellSet.add(cell[i + 1][j]);
        }
        if (j > 1) { // left
            cellSet.add(cell[i][j - 1]);
        }
        if (j < cell[0].length - 1) { // right
            cellSet.add(cell[i][j + 1]);
        }
        if (i > 1 && j > 1) { // upper left
            cellSet.add(cell[i - 1][j - 1]);
        }
        if (i > 1 && j < cell[0].length - 1) { // upper right
            cellSet.add(cell[i - 1][j + 1]);
        }
        if (i < cell.length - 1 && j > 1) { // lower left
            cellSet.add(cell[i + 1][j - 1]);
        }
        if (i < cell.length - 1 && j < cell[0].length - 1) { // lower right
            cellSet.add(cell[i + 1][j + 1]);
        }

        for (Matrix matrix : cellSet) {
            if (matrix.getAlive()) {
                counter++;
            }
        }
        return counter;
    }

    public void clearAllCell() {
        for (Matrix[] matrices : cell) {
            for (Matrix matrix : matrices) {
                matrix.setAlive(false);
            }
        }
    }
}
