package lifegame.view;

import lifegame.game.LifeCell;
import lifegame.game.Matrix;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Harry Chou on 2019/2/26.
 */
public class GameInterface extends JFrame {
    private int width;
    private int height;

    private File file;
    private GameView gameView;

    private GameInterface() {
        // load the width & height from file
        file = new File("config.txt");
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("[^0123456789]+");
            width = scanner.nextInt();
            height = scanner.nextInt();
        } catch (IOException e) {
            e.printStackTrace();
            // default value is used when the configuration file goes wrong
            width = 20;
            height = 50;
        }

        LifeCell lifeCell = new LifeCell();
        setVisible(true);
        setBounds(0, 0, 1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
        JButton initLifeGame = new JButton("Restart");
        JButton startLifeGame = new JButton("Start/Continue");
        JButton stopLifeGame = new JButton("Pause");

        Matrix[][] matrices = new Matrix[width][height];
        for (int i = 0; i < matrices.length; i++) {
            for (int j = 0; j < matrices[i].length; j++) {
                matrices[i][j] = new Matrix();
            }
        }
        lifeCell.setCell(matrices);

        gameView = new GameView();
        gameView.setLifeCell(lifeCell);
        JPanel northPanel = new JPanel();
        JLabel tips = new JLabel("Left click to set a cell alive, right click to cancel.");
        northPanel.add(tips);
        JPanel southPanel = new JPanel();
        southPanel.add(gameView.fixMess);
        southPanel.add(startLifeGame);
        southPanel.add(stopLifeGame);
        southPanel.add(initLifeGame);
        southPanel.add(gameView.showMess);
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(gameView, BorderLayout.CENTER);

        initLifeGame.addActionListener(e -> {
            gameView.fixMess.setText(null);
            gameView.clearAllCell();
            gameView.generationNumber = 0;
        });

        startLifeGame.addActionListener(e -> gameView.timer.start());

        stopLifeGame.addActionListener(e -> gameView.timer.stop());

        gameView.repaint();
        validate();
    }

    public static void main(String[] args) {
        GameInterface gameInterface = new GameInterface();
    }
}
