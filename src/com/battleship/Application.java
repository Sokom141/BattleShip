package com.battleship;

import com.battleship.GUI.Window;

import java.awt.*;

public class Application {

    /**
     * Point of start of the game
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(Window::new);
    }

}
