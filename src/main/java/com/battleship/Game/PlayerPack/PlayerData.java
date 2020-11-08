package com.battleship.Game.PlayerPack;

import javax.swing.*;
import java.io.Serializable;

public class PlayerData implements Serializable {

    private final String userName;
    private final ImageIcon avatarImage;


    public PlayerData(String name, ImageIcon avatar) {
        userName = name;
        avatarImage = avatar;
    }

    public String getName() {
        return userName;
    }

    public ImageIcon getAvatar() {
        return avatarImage;
    }
}