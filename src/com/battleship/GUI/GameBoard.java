package com.battleship.GUI;

import com.battleship.Networking.Client;
import com.battleship.Networking.NetworkConnection;
import com.battleship.Networking.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard {

    private final JFrame frame;
    private final JPanel mainPanel;
    private JPanel chatPanel;
    private JPanel gameBoardPanel;
    private JScrollPane scrollPane;
    private final JTextArea messages = new JTextArea();
    private final JTextField input = new JTextField();

    private NetworkConnection connection;

    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final JButton[][] positions = new JButton[10][10];


    public GameBoard() {

        frame = new JFrame("Battleship Game");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(0, 2));

        this.initBoard();
        this.initChat();

        mainPanel.add(gameBoardPanel);
        mainPanel.add(chatPanel);

        this.setButtons();

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(1000, 500); //TODO: set a good size for the game.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * Initializes the board components
     */
    private void initBoard() {
        gameBoardPanel = new JPanel();
        gameBoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));
        gameBoardPanel.setLayout(new GridLayout(10, 10));
    }

    /**
     * Initializes the chat components
     */
    private void initChat() {
        chatPanel = new JPanel();
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        messages.setMaximumSize(new Dimension(500, 900));
        input.setMaximumSize(new Dimension(500, 100));
        input.addActionListener(buttonHandler);

        scrollPane = new JScrollPane(messages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatPanel.add(scrollPane);
        chatPanel.add(input);
    }

    /**
     * Set the buttons of the game board.
     * Every ShipUnit correspond to one button
     */
    private void setButtons() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions[i][j] = new JButton();

                positions[i][j].setBackground(Color.LIGHT_GRAY);

                gameBoardPanel.add(positions[i][j]);
                positions[i][j].addActionListener(buttonHandler);
            }
        }
    }

    /**
     * Called if the Player is the server
     */
    public void createServer(int port) {
        connection = new Server(data -> SwingUtilities.invokeLater(() -> messages.append(data.toString() + "\n")), port);
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
        connection = new Client(data -> SwingUtilities.invokeLater(() -> messages.append(data.toString() + "\n")), ip, port);
        try {
            connection.startConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                String message = "Msg: ";
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
                testPrint(source);
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
                    if (src == positions[i][j]) {
                        System.out.println("Chosen position to attack: " + i + ", " + j);
                        positions[i][j].setBackground(Color.BLUE);
                    }
                }
            }
        }
    }
}