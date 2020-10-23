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

        //pack()
        setMinimumSize(new Dimension(300, 300));

        setTitle("BattleShip Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
