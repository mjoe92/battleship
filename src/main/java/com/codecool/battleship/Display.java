package com.codecool.battleship;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame{

    public static JButton cells[] = new JButton[100];
    public static JPanel panel = new JPanel();
    public static JFrame frame = new JFrame();

    public static void displayInitializer(char[][] playerBoard) {
        frame.setTitle("BattleShip");
        frame.setSize(500,500);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(playerBoard.length, playerBoard[0].length));
        for (int i = 0; i < playerBoard.length * playerBoard[0].length; i++) {
            cells[i] = new JButton();
            panel.add(cells[i]);
        }
        frame.add(panel);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        for (int j = 0; j < playerBoard.length; j++) {
            for (int m = 0; m < playerBoard[j].length; m++) {
                cells[(j*playerBoard.length)+m].setText(String.valueOf(playerBoard[j][m]));
            }
        }
    }

    public static void reDrawDisplay(char[][] playerBoard, String playerName) {
        frame.setTitle(playerName);
        for (int j = 0; j < playerBoard.length; j++) {
            for (int m = 0; m < playerBoard[j].length; m++) {
                cells[(j*playerBoard.length)+m].setText(String.valueOf(playerBoard[j][m]));
            }
        }
    }

}
