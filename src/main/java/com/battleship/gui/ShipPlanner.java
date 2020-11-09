package com.battleship.gui;

import com.battleship.game.boardpack.Board;
import com.battleship.game.shippack.Ship;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
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

    private final boolean isServer;
    private final int port;
    private final String ip;

    public ShipPlanner(boolean isServer, int port, String ip) {

        frame = new JFrame("Place your ships");

        $$$setupUI$$$();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == buttonOk) {
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, panel.getFont()), null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(gridPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(350, 350), new Dimension(350, 350), new Dimension(350, 350), 0, false));
        shipPanel = new JPanel();
        shipPanel.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(shipPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOk = new JButton();
        buttonOk.setEnabled(false);
        buttonOk.setText("OK");
        shipPanel.add(buttonOk, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxShipSelector = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("4 unit ship");
        defaultComboBoxModel1.addElement("3 unit ship (1)");
        defaultComboBoxModel1.addElement("3 unit ship (2)");
        defaultComboBoxModel1.addElement("2 unit ship (1)");
        defaultComboBoxModel1.addElement("2 unit ship (2)");
        defaultComboBoxModel1.addElement("2 unit ship (3)");
        defaultComboBoxModel1.addElement("1 unit ship (1)");
        defaultComboBoxModel1.addElement("1 unit ship (2)");
        defaultComboBoxModel1.addElement("1 unit ship (3)");
        defaultComboBoxModel1.addElement("1 unit ship (4)");
        comboBoxShipSelector.setModel(defaultComboBoxModel1);
        shipPanel.add(comboBoxShipSelector, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonReset = new JButton();
        buttonReset.setText("Reset");
        shipPanel.add(buttonReset, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leftClickLabelHelper = new JLabel();
        leftClickLabelHelper.setText("Left-Click: vertical placement");
        shipPanel.add(leftClickLabelHelper, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        shipPanel.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        rightClickLabelHelper = new JLabel();
        rightClickLabelHelper.setText("Right-Click: horizontal placement");
        shipPanel.add(rightClickLabelHelper, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        shipPanel.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
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
                                verticalShipsSurroundingArea(i, j, shipLen); //manages the surrounding area of the vertical ships
                            } else {
                                horizontalShipsSurroundingArea(i, j, shipLen);//manages the surrounding area of the horizontal ships
                            }
                            if (comboBoxItemCount == 1) {
                                buttonOk.setEnabled(true);
                            }
                        }
                    }
                }
            }
        }

        private void verticalShipsSurroundingArea(int i, int j, int shipLen) {
            if (j + shipLen <= 10 && isValidPosition(i, j, i, j + shipLen - 1)) {

                for (int l = j; l < j + shipLen; l++) { // disables the surrounding ship area
                    this.disableSurrounding(i, l);
                }
                for (int l = j; l < j + shipLen; l++) {
                    positions[i][l].setBackground(Color.BLUE); // sets the ships color to Color.BLUE
                }
                board.addShip(new Ship(i, j, i, j + shipLen), (String) comboBoxShipSelector.getSelectedItem());
                comboBoxShipSelector.removeItem(comboBoxShipSelector.getSelectedItem());
            }
        }

        private void horizontalShipsSurroundingArea(int i, int j, int shipLen) {
            if (i + shipLen <= 10 && isValidPosition(i, j, i + shipLen - 1, j)) {
                for (int l = i; l < i + shipLen; l++) { // disables the surrounding ship area
                    this.disableSurrounding(l, j);
                }
                for (int l = i; l < i + shipLen; l++) { // sets the ships color to Color.BLUE
                    positions[l][j].setBackground(Color.BLUE);
                }
                board.addShip(new Ship(i, j, i + shipLen, j), (String) comboBoxShipSelector.getSelectedItem());
                comboBoxShipSelector.removeItem(comboBoxShipSelector.getSelectedItem());
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
                        //if (i != x && j != y) { // Not working properly
                        positions[i][j].setBackground(new Color(175, 175, 175));
                        //}
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
