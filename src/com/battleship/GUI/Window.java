package com.battleship.GUI;

import com.battleship.Networking.NetworkConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Window {

    /**
     *
     */
    private static final long serialVersionUID = 4453499308378636423L;
    private JPanel panel;
    private JButton b_start_host;
    private JButton b_start_join;
    private JButton b_exit;
    private JButton b_settings;
    private final EventHandler eventHandler = new EventHandler();
    private final Window ref = this;
    private JLabel gameName;
    protected String ip;
    protected int port;
    private NetworkConnection connection;

    /**
     * Constructor of the Window class
     */
    public Window() {
        initUI();
    }

    /**
     * Initializes the User Interface
     */
    private void initUI() {

        JFrame frame = new JFrame("BattleShip Game");


        b_exit.addActionListener(eventHandler);
        b_start_host.addActionListener(eventHandler);
        b_start_join.addActionListener(eventHandler);
        b_settings.addActionListener(eventHandler);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Private class to handle events
     */
    private class EventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Object source = e.getSource();
            if (source == b_exit) {

                System.exit(0);

            } else if (source == b_start_host) {
                SwingUtilities.invokeLater(() -> {
                    ServerDialog serverDialog = new ServerDialog(ref);

                    GameBoard gb = new GameBoard();
                    gb.createServer(port);
                });


            } else if (source == b_start_join) {
                SwingUtilities.invokeLater(() -> {
                    ClientDialog clientDialog = new ClientDialog(ref);

                    GameBoard gb = new GameBoard();
                    gb.createClient(ip, port);
                });
            } else if (source == b_settings) {
                    new Settings();
                }
            }
        }
    }
