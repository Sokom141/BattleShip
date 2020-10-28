package com.battleship.GUI;

import com.battleship.Networking.NetworkConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 4453499308378636423L;
    private JFrame frame;
    private JPanel panel;
    private JButton b_start_host;
    private JButton b_start_join;
    private JButton b_exit;
    private JButton avatar_button;
    private JTextField tf_name;
    private ImageIcon user_avatar;

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

        frame = new JFrame();
        frame.setTitle("BattleShip Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setPreferredSize(new Dimension(800, 800));

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));

        b_start_host = new JButton("Host");
        b_start_join = new JButton("Join");
        b_exit = new JButton("Exit");
        b_exit.addActionListener(this);
        b_start_host.addActionListener(this);
        b_start_join.addActionListener(this);

        tf_name = new JTextField("Enter your name");
        user_avatar = loadImage("src/resources/anonymous.png");
        avatar_button = new JButton(user_avatar);
        avatar_button.addActionListener(this);

        panel.add(b_start_host);
        panel.add(b_start_join);
        panel.add(b_exit);
        panel.add(tf_name);
        panel.add(avatar_button);


        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ImageIcon loadImage(String path) {

        return new ImageIcon(path);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source == b_exit) {

            System.exit(0);

        } else if (source == b_start_host) {
            SwingUtilities.invokeLater(() -> {
                GameBoard gb = new GameBoard();
                gb.createServer();
            });


        } else if (source == b_start_join) {
            SwingUtilities.invokeLater(() -> {
                GameBoard gb = new GameBoard();
                gb.createClient("127.0.0.1", 6969);
            });

        } else if (source == avatar_button) {
            SwingUtilities.invokeLater(() -> {
                ImageChooser imageChooser = new ImageChooser(avatar_button);
            });
        }
    }
}