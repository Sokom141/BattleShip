package com.battleship.GUI;

import com.battleship.Game.BoardPack.Board;
import com.battleship.Game.ShipPack.Ship;
import com.battleship.Networking.Client;
import com.battleship.Networking.NetworkConnection;
import com.battleship.Networking.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard {

    private JPanel mainPanel;
    private JPanel chatPanel;
    private JPanel gameBoard1;
    private JPanel gameBoard2;
    private JScrollPane scrollPane;
    private JTextArea messages;
    private JTextField input;
    private JLabel field1Label;
    private JLabel field2Label;

    private NetworkConnection connection;

    private final ButtonHandler buttonHandler = new ButtonHandler();
    private final JButton[][] positions1 = new JButton[10][10];
    private final JButton[][] positions2 = new JButton[10][10];



    public GameBoard() {

        JFrame frame = new JFrame("Battleship Game");

        input.addActionListener(buttonHandler);
        this.setButtons1();
        this.setShipOnBoard();
        this.setButtons2();
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
        System.out.println("-------------------------");
        for(Ship i : ShipPlanner.board.field.values())
            System.out.println(i);
        for(String i : ShipPlanner.board.field.keySet())
            System.out.println(i);

        for(String name : ShipPlanner.board.field.keySet()){

            Ship shipToPlace = getShip(name);

            int[] h_c = shipToPlace.getHeadCoordinates();
            int length = shipToPlace.getLength();

            if(shipToPlace.isVertical()){

                for(int i=0; i < length; i++){
                    //positions1[h_c[1]][h_c[2] + i] = new JButton();
                    positions1[h_c[0]][h_c[1] + i].setBackground(Color.BLUE);
                    //gameBoard1.add(positions1[h_c[1]][h_c[2] +i]);
                }
            } else {

                for(int i=0; i < length; i++){
                    //positions1[h_c[1]][h_c[2] + i] = new JButton();
                    positions1[h_c[0] + i][h_c[1]].setBackground(Color.BLUE);
                    //gameBoard1.add(positions1[h_c[1] + i][h_c[2]]);
                }
            }

        }
    }

    /**
     *
     * @param key = the String in the Hashmap related to the Ship object
     * @return The ship related to the key String
     */
    private Ship getShip(String key){

         Ship shipToGet = ShipPlanner.board.field.get(key);

         return shipToGet;

    }

    /**
     * Set the buttons of the first game board.
     * Every ShipUnit correspond to one button
     * Same on the method below but for the second game board.
     */
    private void setButtons1() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions1[i][j] = new JButton();
                positions1[i][j].setBackground(Color.LIGHT_GRAY);
                positions1[i][j].setEnabled(false);
                gameBoard1.add(positions1[i][j]);
            }
        }
    }
    private void setButtons2() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                positions2[i][j] = new JButton();

                positions2[i][j].setBackground(Color.LIGHT_GRAY);

                gameBoard2.add(positions2[i][j]);
                positions2[i][j].addActionListener(buttonHandler);
            }
        }
    }

    private void createUIComponents() {
        gameBoard1 = new JPanel();
        gameBoard2 = new JPanel();
        gameBoard1.setLayout(new GridLayout(10,10));
        gameBoard2.setLayout(new GridLayout(10,10));
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
                    if(src == positions2[i][j]) {
                        System.out.println("Chosen position to attack: " + i + ", " + j);
                        positions2[i][j].setBackground(Color.RED);
                    }
                }
            }
        }
    }
}