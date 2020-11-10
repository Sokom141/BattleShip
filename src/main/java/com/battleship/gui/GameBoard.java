package com.battleship.gui;

import com.battleship.game.playerpack.Player;
import com.battleship.game.playerpack.PlayerData;
import com.battleship.game.shippack.Ship;
import com.battleship.networking.Client;
import com.battleship.networking.NetworkConnection;
import com.battleship.networking.Server;
import com.battleship.utils.BSConfigFile;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameBoard {

    private final JButton[][] playerPositions = new JButton[10][10];
    private JPanel mainPanel;
    private JPanel chatPanel;
    private JPanel gameBoard1;
    private JPanel gameBoard2;
    private JScrollPane scrollPane;
    private JTextArea messages;
    private JTextField input;
    private final JButton[][] enemyPositions = new JButton[10][10];
    private final int PLAYING = 0;

    private NetworkConnection connection;

    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final int SHIP_HIT = 1;
    private final int GAME_WON = 2;
    private JFrame frame;
    private JLabel playerFieldLabel;
    private JLabel enemyFieldLabel;
    private boolean isUserDataSet = false;
    private boolean isUserTurn = Player.isHost();
    private final int resolutionWidth = Integer.parseInt(BSConfigFile.readProperties("Resolution_Width"));
    private final int resolutionHeight = Integer.parseInt(BSConfigFile.readProperties("Resolution_Height"));

    public GameBoard() {
        frame = new JFrame("Battleship Game");

        $$$setupUI$$$();
        input.addActionListener(buttonHandler);
        this.setPlayerButtons();
        this.setShipOnBoard();
        this.setEnemyButtons();
        this.setUserElements();

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(new Dimension(resolutionWidth, resolutionHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Gets all the Ship Objects from HashMap and read their position
     * Then place them on another (disabled) grid layout of buttons
     */
    private void setShipOnBoard() {

        Color shipColor = BSConfigFile.manageColors(BSConfigFile.readProperties("Color"));
        for (Ship shipToPlace : ShipPlanner.board.field.values()) {

            int[] h_c = shipToPlace.getHeadCoordinates();
            int length = shipToPlace.getLength();

            if (shipToPlace.isVertical()) {

                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0]][h_c[1] + i].setBackground(shipColor);
                    playerPositions[h_c[0]][h_c[1] + i].setEnabled(true);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0] + i][h_c[1]].setBackground(shipColor);
                    playerPositions[h_c[0] + i][h_c[1]].setEnabled(true);
                }
            }

        }
    }

    /**
     * Set the buttons of the first game board.
     * Every ShipUnit correspond to one button
     * Same on the method below but for the second game board.
     */
    private void setPlayerButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) { // TODO: we should see if this implementation is better than the one below
                JButton currentButton = new JButton();
                currentButton.setBackground(Color.LIGHT_GRAY);
                currentButton.setEnabled(false);
                gameBoard1.add(currentButton);
                playerPositions[i][j] = currentButton;
            }
        }
    }

    private void setEnemyButtons() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                enemyPositions[i][j] = new JButton();
                enemyPositions[i][j].setBackground(Color.LIGHT_GRAY);
                gameBoard2.add(enemyPositions[i][j]);
                enemyPositions[i][j].addActionListener(buttonHandler);
            }
        }
    }

    /**
     * Custom constructor of .form file
     */
    private void createUIComponents() {
        gameBoard1 = new JPanel();
        gameBoard2 = new JPanel();
        gameBoard1.setLayout(new GridLayout(10, 10));
        gameBoard2.setLayout(new GridLayout(10, 10));
    }

    private void setUserElements() {
        playerFieldLabel.setText(Player.getName() + "'s field");
        playerFieldLabel.setIcon(Player.getAvatar());
    }

    /**
     * Called if the Player is the server
     *
     * @param port the port where the game will be hosted
     */
    public void createServer(int port) {
        connection = new Server(data -> SwingUtilities.invokeLater(() -> handleData(data)), port);
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called if the Player is the client
     *
     * @param ip   the IP of the server to connect
     * @param port the port of the server to connect
     */
    public void createClient(String ip, int port) {
        connection = new Client(data -> SwingUtilities.invokeLater(() -> handleData(data)), ip, port);
        try {
            connection.startConnection();
            try {
                Thread.sleep(300); // inefficient. We should really use another method
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendUserData() {
        try {
            connection.send(new PlayerData(Player.getName(), Player.getAvatar()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: refactoring
    private void handleData(Object data) {
        if (data instanceof String) {
            messages.append(data.toString() + "\n");

        } else if (data instanceof PlayerData) {
            if (!isUserDataSet) {
                PlayerData enemy = (PlayerData) data;
                enemyFieldLabel.setText(enemy.getName() + "'s field");
                enemyFieldLabel.setIcon(enemy.getAvatar());
                sendUserData();
                isUserDataSet = true;
            }
        } else {
            int[] posToAttack = (int[]) data;

            if (posToAttack[0] == SHIP_HIT) { // The enemy sends back the int array with a 1 in first position to signal that he has been hit
                enemyPositions[posToAttack[1]][posToAttack[2]].setBackground(Color.RED);
                isUserTurn = true;
            } else if (posToAttack[0] == GAME_WON) {
                JOptionPane.showMessageDialog(frame,
                        "You won!",
                        "Congratulations",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (playerPositions[posToAttack[1]][posToAttack[2]].isEnabled()) {
                    playerPositions[posToAttack[1]][posToAttack[2]].setBackground(Color.BLACK);
                    playerPositions[posToAttack[1]][posToAttack[2]].setEnabled(false);

                    try {
                        if (hasPlayerWin()) {
                            connection.send(new int[]{GAME_WON, posToAttack[1], posToAttack[2]});
                            JOptionPane.showMessageDialog(frame,
                                    "You lost!",
                                    "Bad news",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            connection.send(new int[]{SHIP_HIT, posToAttack[1], posToAttack[2]});
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    isUserTurn = true;
                }
            }
        }
    }

    private boolean hasPlayerWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (playerPositions[i][j].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setForeground(new Color(-4473925));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        mainPanel.add(gameBoard1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(chatPanel, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(250, 250), new Dimension(250, 500), null, 0, false));
        chatPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), "Chat", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        scrollPane = new JScrollPane();
        chatPanel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        messages = new JTextArea();
        messages.setEditable(false);
        Font messagesFont = this.$$$getFont$$$("Roboto Light", -1, -1, messages.getFont());
        if (messagesFont != null) messages.setFont(messagesFont);
        scrollPane.setViewportView(messages);
        input = new JTextField();
        Font inputFont = this.$$$getFont$$$("Roboto Light", -1, -1, input.getFont());
        if (inputFont != null) input.setFont(inputFont);
        chatPanel.add(input, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        mainPanel.add(gameBoard2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(400, 400), new Dimension(400, 400), new Dimension(400, 400), 0, false));
        gameBoard2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        playerFieldLabel = new JLabel();
        playerFieldLabel.setText("Your Field");
        mainPanel.add(playerFieldLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enemyFieldLabel = new JLabel();
        enemyFieldLabel.setText("Enemy field");
        mainPanel.add(enemyFieldLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return mainPanel;
    }

    /**
     * Private class to handle buttons.
     */
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Object source = e.getSource();

            if (source == input) {

                System.out.println(input.getText());
                String message = Player.getName() + ": ";
                message += input.getText();
                input.setText("");

                messages.append(message + "\n");

                try {
                    connection.send(message);
                } catch (Exception ex) {
                    messages.append("Failed to send\n");
                    ex.printStackTrace();
                }
            } else {
                if (isUserTurn) {
                    testPrint(source);
                }
            }
        }

        /**
         * Test print function to see the chosen position to attack. Should be removed.
         *
         * @param src Button source
         */
        private void testPrint(Object src) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (src == enemyPositions[i][j]) {
                        System.out.println("Chosen position to attack: " + i + ", " + j);
                        enemyPositions[i][j].setBackground(Color.ORANGE);
                        try {
                            connection.send(new int[]{PLAYING, i, j});
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        enemyPositions[i][j].setEnabled(false);
                        isUserTurn = false;
                    }
                }
            }
        }
    }


}