package com.battleship.GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 4453499308378636423L;

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


        setTitle("BattleShip Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 800));

        Panel pnl = new Panel();
        this.add(pnl);

        Button b_start = new Button("Start");
        Button b_exit = new Button("Exit");
        TextField tf_name = new TextField("Enter your name");

        pnl.add(b_start);
        pnl.add(b_exit);
        pnl.add(tf_name);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
