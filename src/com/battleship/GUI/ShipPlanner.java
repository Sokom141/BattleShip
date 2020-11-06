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

public class ShipPlanner implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JPanel gridPanel;
    private JPanel shipPanel;
    private JButton buttonOk;
    private JComboBox<String> comboBoxShipSelector;
    private JButton buttonReset;
    private JLabel leftClickLabelHelper;
    private JLabel rightClickLabelHelper;
    private JTextArea messages;
    private final JButton[][] positions = new JButton[10][10];
    private final ButtonHandler buttonHandler = new ButtonHandler();

    public static final Board board = new Board();

    private boolean isServer;
    private int port;
    private String ip;

    public ShipPlanner(boolean isServer, int port, String ip) {

        frame = new JFrame("Place your ships");

        buttonOk.addActionListener(this);
        buttonReset.addActionListener(this);
        this.setButtons();

        frame.add(panel, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setSize(1000, 500); //TODO: set a good size for the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.isServer = isServer;
        this.port = port;
        this.ip = ip;
    }

    /**
     * Custom component creation
     */
    private void createUIComponents() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(10, 10));
    }

    /**
     * This fills the panel with buttons
     */
    private void  setButtons() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new JButton();

                positions[i][j].setBackground(Color.LIGHT_GRAY);

                gridPanel.add(positions[i][j]);
                positions[i][j].addMouseListener(buttonHandler);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==buttonOk) {
            SwingUtilities.invokeLater(() -> {
                GameBoard gb = new GameBoard();
                if (isServer) {
                    gb.createServer(port);
                } else {
                    gb.createClient(ip, port);
                }
            });
            frame.dispose();
        }
        // To reset the initial config of the field. This deletes all the previously added ships
        else if (source == buttonReset) {

            board.field.clear(); // resets hashmap
            resetPlanner();
        }
    }

    private void resetPlanner() {
        comboBoxShipSelector.removeAllItems();
        comboBoxShipSelector.addItem("4 Unit Ship");
        comboBoxShipSelector.addItem("3 Unit Ship (1)");
        comboBoxShipSelector.addItem("3 Unit Ship (2)");
        comboBoxShipSelector.addItem("2 Unit Ship (1)");
        comboBoxShipSelector.addItem("2 Unit Ship (2)");
        comboBoxShipSelector.addItem("2 Unit Ship (3)");
        comboBoxShipSelector.addItem("1 Unit Ship (1)");
        comboBoxShipSelector.addItem("1 Unit Ship (2)");
        comboBoxShipSelector.addItem("1 Unit Ship (3)");
        comboBoxShipSelector.addItem("1 Unit Ship (4)");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j].setEnabled(true);
                positions[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }
        buttonOk.setEnabled(false);
    }

    private class ButtonHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();

            // TODO: refactor
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (source == positions[i][j]) {

                        int comboBoxItemCount = comboBoxShipSelector.getItemCount();

                        if (comboBoxItemCount > 0) {

                            int shipLen = Integer.parseInt(((String) Objects.requireNonNull(comboBoxShipSelector.getSelectedItem())).substring(0, 1));

                            if (SwingUtilities.isRightMouseButton(e)) {
                                if (j + shipLen <= 10 && isValidPosition(i, j, i, j + shipLen - 1)) {
                                    for (int l = j; l < j + shipLen; l++) {
                                        this.disableSurrounding(i, l);
                                        positions[i][l].setBackground(Color.BLUE);
                                    }
                                    board.addShip(new Ship(i, j, i, j + shipLen), (String) comboBoxShipSelector.getSelectedItem());
                                    comboBoxShipSelector.removeItem(comboBoxShipSelector.getSelectedItem());
                                }
                            } else {
                                if (i + shipLen <= 10 && isValidPosition(i, j, i + shipLen - 1, j)) {
                                    for (int l = i; l < i + shipLen; l++) {
                                        this.disableSurrounding(l, j);
                                        positions[l][j].setBackground(Color.BLUE);
                                    }
                                    board.addShip(new Ship(i, j, i + shipLen, j), (String) comboBoxShipSelector.getSelectedItem());
                                    comboBoxShipSelector.removeItem(comboBoxShipSelector.getSelectedItem());
                                }
                            }
                            if (comboBoxItemCount == 1) {
                                buttonOk.setEnabled(true);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Checks if the selected position is valid for placing a ship
         *
         * @param xHead the x head coordinate
         * @param yHead the y head coordinate
         * @param xTail the x tail coordinate
         * @param yTail the y tail coordinate
         * @return true if the position is valid, false otherwise
         */
        private boolean isValidPosition(int xHead, int yHead, int xTail, int yTail) {
            return positions[xHead][yHead].isEnabled() && positions[xTail][yTail].isEnabled();
        }

        private void disableSurrounding(int x, int y) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    try {
                        positions[i][j].setEnabled(false);
                        if (i != x && j != y) { // Not working properly
                            positions[i][j].setBackground(new Color(175, 175, 175));
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        // Nothing to do but maybe there is another way to do this
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
