package com.battleship.GUI;

import com.battleship.Networking.NetworkConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel gameName;

    private final EventHandler eventHandler = new EventHandler();

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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b_exit.addActionListener(eventHandler);
        b_start_host.addActionListener(eventHandler);
        b_start_join.addActionListener(eventHandler);
        b_settings.addActionListener(eventHandler);

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

                SwingUtilities.invokeLater(ServerDialog::new);
            } else if (source == b_start_join) {

                SwingUtilities.invokeLater(ClientDialog::new);
            } else if (source == b_settings) {

                new Settings();
            }
        }
    }
}