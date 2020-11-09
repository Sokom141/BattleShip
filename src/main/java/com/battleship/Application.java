package com.battleship;

import com.battleship.gui.Window;

import java.awt.*;

public class Application {

    /**
     * Point of start of the game
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(Window::new);
    }

}
