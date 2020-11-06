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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class ShipPlanner implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JPanel gridPanel;
    private JPanel shipPanel;
    private JButton buttonOk;
    private JComboBox<String> comboBox1;
    private JButton buttonReset;
    private JTextArea messages;
    private NetworkConnection connection;

    private final JButton[][] positions = new JButton[10][10];

    private final ButtonHandler buttonHandler = new ButtonHandler();

    public static final Board board = new Board();

    public ShipPlanner(){

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

    public static void main(String[] args){
        new ShipPlanner();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==buttonOk) {
            new GameBoard();
            //board.printConfig();
            frame.dispose();
        }
        // To reset the initial config of the field. This deletes all the previously added ships
        else if(source == buttonReset){

            board.field.clear(); // resets hashmap
            resetComboBox();
            for(int i = 0; i < 10; i++){
                for(int j = 0; j < 10; j++){
                    positions[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
            board.printConfig();
        }
    }

    private void resetComboBox(){
        comboBox1.removeAllItems();
        comboBox1.addItem("4 Unit Ship");
        comboBox1.addItem("3 Unit Ship (1)");
        comboBox1.addItem("3 Unit Ship (2)");
        comboBox1.addItem("2 Unit Ship (1)");
        comboBox1.addItem("2 Unit Ship (2)");
        comboBox1.addItem("2 Unit Ship (3)");
        comboBox1.addItem("1 Unit Ship (1)");
        comboBox1.addItem("1 Unit Ship (2)");
        comboBox1.addItem("1 Unit Ship (3)");
        comboBox1.addItem("1 Unit Ship (4)");
    }
    private class ButtonHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Object source = e.getSource();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (source == positions[i][j]) {

                        int k = Integer.parseInt(((String) Objects.requireNonNull(comboBox1.getSelectedItem())).substring(0, 1));

                        if (SwingUtilities.isRightMouseButton(e)) {
                            for (int l = j; l < j + k; l++) {
                                positions[i][l].setBackground(Color.BLUE);
                            }
                            board.addShip(new Ship(i, j, i, j+k), (String) comboBox1.getSelectedItem());
                        } else {
                            for (int l = i; l < i + k; l++) {
                                positions[l][j].setBackground(Color.BLUE);
                            }
                            board.addShip(new Ship(i, j, i+k, j), (String) comboBox1.getSelectedItem());
                        }
                        comboBox1.removeItem(comboBox1.getSelectedItem());
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
