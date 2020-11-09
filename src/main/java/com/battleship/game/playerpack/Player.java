package com.battleship.game.playerpack;

import javax.swing.*;

public class Player {

    private static String name;
    private static boolean host = false;
    private static ImageIcon avatar;

    /**
     * @return the name of the Player
     */
    public static String getName() {
        return name;
    }

    public static void setName(String userName) {
        name = userName;
    }

    /**
     * @return if the Player is the host of the game ( Player 1 )
     */
    public static boolean isHost() {
        return host;
    }

    public static void setHost(boolean isHosting) {
        host = isHosting;
    }

    /**
     * @return the avatar path of the Player
     */
    public static ImageIcon getAvatar() {
        return avatar;
    }

    /**
     * set the avatar of the Player
     *
     * @param path string path for the image
     */
    public static void setAvatar(String path) {
        avatar = new ImageIcon(path);
    }
}