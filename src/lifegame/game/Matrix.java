package lifegame.game;

/**
 * Created by Harry Chou on 2019/2/25.
 * Define the lifegame's matrix.
 */
public class Matrix {
    private int x = 0;
    private int y = 0;
    boolean isAlive = false; // the state of each cell

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getAlive() {
        return isAlive;
    }

}
