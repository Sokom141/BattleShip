package com.battleship.GUI;

import com.battleship.Game.BoardPack.Board;
import com.battleship.Game.ShipPack.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ShipPlanner {
    private JPanel panel;
    private JPanel gridPanel;
    private JPanel shipPanel;
    private JButton buttonOk;
    private JComboBox comboBox1;

    private final JButton[][] positions = new JButton[10][10];

    private final ButtonHandler buttonHandler = new ButtonHandler();

    private final Board board = new Board();

    public ShipPlanner(){
        JFrame frame = new JFrame("Place your ships");

        buttonOk.addMouseListener(buttonHandler);
        this.setButtons();

        frame.add(panel, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setSize(1000, 500); //TODO: set a good size for the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Custom component creation
     */
    private void createUIComponents() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));
    }

    private void setButtons() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new JButton();

                positions[i][j].setBackground(Color.LIGHT_GRAY);

                gridPanel.add(positions[i][j]);
                positions[i][j].addMouseListener(buttonHandler);
            }
        }
    }

    public static void main(String[] args){
        new ShipPlanner();
    }

    private class ButtonHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();

            if (source == buttonOk) {
                board.printConfig();
            } else {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (source == positions[i][j]) {

                            board.addShip(new Ship(i, j, i, j), (String) comboBox1.getSelectedItem());
                            positions[i][j].setBackground(Color.RED);

                            int k = Integer.parseInt(((String)Objects.requireNonNull(comboBox1.getSelectedItem())).substring(0, 1));
                            if (SwingUtilities.isRightMouseButton(e)) {
                                for (int l = j; l < j + k; l++) {
                                    positions[i][l].setBackground(Color.RED);
                                }
                            } else {
                                for (int l = i; l < i + k; l++) {
                                    positions[l][j].setBackground(Color.RED);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }

    }
}
