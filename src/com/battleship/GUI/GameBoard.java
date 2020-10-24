package com.battleship.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard {

    private JFrame frame;
    private JPanel panel;

    private JButton[][] positions = new JButton[10][10];
    private ButtonHandler buttonHandler;


    public GameBoard() {

        frame = new JFrame("Battleship Game");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(10, 10));
        buttonHandler = new ButtonHandler();
        this.setButtons();
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void setButtons() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new JButton();

                positions[i][j].setBackground(Color.LIGHT_GRAY);

                panel.add(positions[i][j]);
                positions[i][j].addActionListener(buttonHandler);
            }
        }
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            testPrint(source);
        }

        private void testPrint(Object src) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (src == positions[i][j]) {
                        System.out.println("Chosen position to attack: " + i + ", " + j);
                        positions[i][j].setBackground(Color.BLUE);
                    }
                }
            }
        }
    }
}