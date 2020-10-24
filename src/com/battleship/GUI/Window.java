package com.battleship.GUI;

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
    private Button b_start_host;
    private Button b_start_join;
    private Button b_exit;
    private JTextField tf_name;
    private ImageIcon user_avatar;

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
        //setPreferredSize(new Dimension(800, 800));

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 2));

        b_start_host = new Button("Host");
        b_start_join = new Button("Join");
        b_exit = new Button("Exit");
        b_exit.addActionListener(this);
        b_start_host.addActionListener(this);

        tf_name = new JTextField("Enter your name");
        user_avatar = loadImage();
        JLabel avatar_label = new JLabel(user_avatar);

        panel.add(b_start_host);
        panel.add(b_start_join);
        panel.add(b_exit);
        panel.add(tf_name);
        panel.add(avatar_label);


        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private ImageIcon loadImage() {

        return new ImageIcon("src/resources/anonymous.png");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if (source == b_exit) {
            System.exit(0);
        } else if (source == b_start_host) {
            new GameBoard();
        }
    }
}
