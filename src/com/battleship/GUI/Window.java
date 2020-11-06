package com.battleship.GUI;

import com.battleship.Game.PlayerPack.Player;
import com.battleship.Networking.NetworkConnection;
import com.battleship.utils.BSConfigFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {

    /**
     *
     */
    private static final long serialVersionUID = 4453499308378636423L;
    private JFrame frame;
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

        frame = new JFrame("BattleShip Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b_exit.addActionListener(eventHandler);
        b_start_host.addActionListener(eventHandler);
        b_start_join.addActionListener(eventHandler);
        b_settings.addActionListener(eventHandler);

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        this.initPlayer();
    }

    private void initPlayer() {
        Player.setName(BSConfigFile.readProperties("Name"));
        Player.setAvatar(BSConfigFile.readProperties("Avatar_Path"));
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
                Player.setHost(true);
                frame.dispose();

            } else if (source == b_start_join) {

                SwingUtilities.invokeLater(ClientDialog::new);
                frame.dispose();

            } else if (source == b_settings) {

                SwingUtilities.invokeLater(Settings::new);

            }
        }
    }
}