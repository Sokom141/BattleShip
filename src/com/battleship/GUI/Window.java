package com.battleship.GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        initUI();
    }

    private void initUI(){

        //pack()
        setMinimumSize(new Dimension(300, 300));

        setTitle("BattleShip Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
