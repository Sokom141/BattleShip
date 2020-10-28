package com.battleship.Game.PlayerPack;

import javax.swing.*;

public class Player {

    private final String name;
    private final boolean host;
    private final ImageIcon avatar;

    /**
     * Constructor for the Player class
     *
     * @param user_name  The name the user has chosen
     * @param is_hosting True if the Player is the server
     * @param image      The user image
     */
    public Player(String user_name, boolean is_hosting, ImageIcon image) {
        name = user_name;
        host = is_hosting;
        avatar = image;
    }

    /**
     * @return the name of the Player
     */
    public String getName() {
        return name;
    }

    /**
     * @return if the Player is the host of the game ( Player 1 )
     */
    public boolean isHost() {
        return host;
    }

    /**
     * @return the avatar image of the Player
     */
    public ImageIcon getAvatar() {
        return avatar;
    }
}