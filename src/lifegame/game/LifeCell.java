package lifegame.game;

import java.util.*;

/**
 * Created by Harry Chou on 2019/2/25.
 */
public class LifeCell {
    public Matrix[][] cell; // define each cell
    Set<Matrix> cellSet = new HashSet<>(); //define the surrounding 8 cells

    public void setCell(Matrix[][] cell) {
        this.cell = cell;
    }

    /**
     * get the next generation of matrix
     */
    public Matrix[][] getNextGeneration() {
        Matrix[][] newCellSet = new Matrix[cell.length - 1][cell[0].length - 1]; // get a copy of the old matrix
        for (int i = 0; i < newCellSet.length; i++) {
            for (int j = 0; j < newCellSet[i].length; j++) {
                int countAliveCell = aliveCellCounter(i, j, newCellSet);
                if (countAliveCell == 3) {
                    newCellSet[i][j].setAlive(true);
                } else if (countAliveCell == 2) {
                    newCellSet[i][j].setAlive(cell[i][j].getAlive());
                }
            }
        }
        return newCellSet;
    }

    /**
     * get the number of live cells around each cell
     */
    private int aliveCellCounter(int i, int j, Matrix[][] cell) {
        int counter = 0;
        cellSet.clear();
        if (i > 0) { // up
            cellSet.add(cell[i - 1][j]);
        }
        if (i < cell.length - 1) { //down
            cellSet.add(cell[i + 1][j]);
        }
        if (j > 0) { // left
            cellSet.add(cell[i][j - 1]);
        }
        if (j < cell[0].length - 1) { // right
            cellSet.add(cell[i][j + 1]);
        }
        if (i > 0 && j > 0) { // upper left
            cellSet.add(cell[i - 1][j - 1]);
        }
        if (i > 0 && j < cell[0].length - 1) { // upper right
            cellSet.add(cell[i - 1][j + 1]);
        }
        if (i < cell.length - 1 && j > 0) { // lower left
            cellSet.add(cell[i + 1][j - 1]);
        }
        if (i < cell.length - 1 && j < cell[0].length - 1) { // lower right
            cellSet.add(cell[i + 1][j + 1]);
        }

        Iterator<Matrix> iterator = cellSet.iterator();
        while (iterator.hasNext()) {
            Matrix matrix = iterator.next();
            if (matrix.isAlive) {
                counter++;
            }
        }
        return counter;
    }

    public void clearAllCell(){
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j].setAlive(false);
            }
        }
    }
}