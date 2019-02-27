package lifegame.view;

import lifegame.game.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Harry Chou on 2019/2/25.
 */
public class GameView extends JPanel implements ActionListener {
    private LifeCell lifeCell;
    Matrix[][] cell;

    private List<Matrix[][]> list; // help to judge whether a cycle appears

    int width = 25;
    int height = 25;
    // the coordinates of the origin
    private int originX = 1;
    private int originY = 1;

    JLabel showMess;
    JLabel fixMess;

    Timer timer; // start/stop the game

    int generationNumber = 0; // number of the new matrix's generation
    int cycleNumber = -1; // cycle length

    private InitGame initGame;

    public GameView() {
        list=new ArrayList<>();
        setBackground(Color.white);
        timer = new Timer(500, this);
        initGame = new InitGame();
        initGame.setGameView(this);
        addMouseListener(initGame); // click on the cell to set it alive
        addMouseMotionListener(initGame);
        showMess = new JLabel();
        fixMess = new JLabel();
        showMess.setFont(new Font("", Font.BOLD, 24));
        showMess.setText("No. " + generationNumber + " generation");
        fixMess.setFont(new Font("", Font.BOLD, 24));
        fixMess.setForeground(Color.red);
    }

    void setLifeCell(LifeCell lifeCell) {
        this.lifeCell = lifeCell;
        cell = lifeCell.cell;
        initCell();
        repaint();
    }

    void clearAllCell() {
        generationNumber = 0;
        cycleNumber = -1;
        list = new ArrayList<>();
        timer.stop();
        lifeCell.clearAllCell();
        showMess.setText("No. " + generationNumber + " generation");
        fixMess.setText("");
        repaint();
    }

    private void initCell() {
        generationNumber = 0;
        cycleNumber = -1;
        // set the coordinate system
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                cell[i][j].setX(j * width + originX);
                cell[i][j].setY(i * height + originY);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color color = new Color(200, 200, 200);
        for (Matrix[] matrices : cell) {
            for (Matrix matrix : matrices) {
                g.setColor(color);
                g.drawRect(matrix.getX(), matrix.getY(), width, height);
                if (matrix.getAlive()) {
                    g.setColor(Color.black);
                    g.fillOval(matrix.getX(), matrix.getY(), width, height);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        generationNumber++;
        Matrix[][] matrices = copyCell(cell);
        if (list.size() == 0) {
            list.add(matrices);
        } else {
            if (cycleFound() == -1) {
                list.add(matrices);
            }
        }
        cell = lifeCell.getNextGeneration();

        showMess.setText("No. " + generationNumber + " generation");
        showMess.repaint();

        int generationShowCycle;
        if (((generationShowCycle = cycleFound()) != -1) && (cycleNumber == -1)) {
            cycleNumber = generationNumber;
            fixMess.setText("The cycle starts at the No. " + cycleNumber + " generation, lasting for " + (list.size() - generationShowCycle) + " rounds.");
        }
    }

    public Matrix[][] copyCell(Matrix[][] cell) {
        Matrix[][] matrices = new Matrix[cell.length][cell[0].length];
        for (int i = 0; i < cell.length; i++) {
            for (int j = 0; j < cell[i].length; j++) {
                matrices[i][j] = new Matrix();
                matrices[i][j].setAlive(cell[i][j].getAlive());
            }
        }
        return matrices;
    }

    private int cycleFound() {
        boolean foundCycle; // whether a cycle is found
        int flag = -1;
        for (int i = 0; i < list.size(); i++) {
            foundCycle = true;
            Matrix[][] matrices = list.get(i);
            for (int j = 0; j < cell.length; j++) {
                for (int k = 0; k < cell[j].length; k++) {
                    if (cell[j][k].getAlive() != matrices[j][k].getAlive()) {
                        foundCycle = false;
                        break;
                    }
                }
                if (!foundCycle) {
                    break;
                }
            }
            if (foundCycle) {
                flag = i;
                break;
            }
        }
        return flag;
    }
}
