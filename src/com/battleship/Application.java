package com.battleship;

import com.battleship.GUI.Window;

import java.awt.*;

public class Application {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Window root = new Window();
        });
    }

}
