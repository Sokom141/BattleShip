package com.battleship.GUI;

import com.battleship.Game.PlayerPack.Player;
import com.battleship.Game.PlayerPack.PlayerData;
import com.battleship.Game.ShipPack.Ship;
import com.battleship.Networking.Client;
import com.battleship.Networking.NetworkConnection;
import com.battleship.Networking.Server;

import javax.swing.*;
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

    public GameBoard() {
        frame = new JFrame("Battleship Game");

        input.addActionListener(buttonHandler);
        this.setPlayerButtons();
        this.setShipOnBoard();
        this.setEnemyButtons();
        this.setUserElements();

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(1000, 800); //TODO: set a good size for the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Gets all the Ship Objects from HashMap and read their position
     * Then place them on another (disabled) grid layout of buttons
     */
    private void setShipOnBoard(){

        for (Ship shipToPlace : ShipPlanner.board.field.values()) {

            int[] h_c = shipToPlace.getHeadCoordinates();
            int length = shipToPlace.getLength();

            if (shipToPlace.isVertical()) {

                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0]][h_c[1] + i].setBackground(Color.BLUE);
                    playerPositions[h_c[0]][h_c[1] + i].setEnabled(true);
                }
            } else {
                for (int i = 0; i < length; i++) {
                    playerPositions[h_c[0] + i][h_c[1]].setBackground(Color.BLUE);
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