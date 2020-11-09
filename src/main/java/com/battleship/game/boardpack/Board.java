package com.battleship.game.boardpack;

import com.battleship.game.shippack.Ship;

import java.util.HashMap;

public class Board {
 //TODO:  delete unnecessary comments
    //private Ship[] field;
    //private int shipsOnField = 0;
    public HashMap<String, Ship> field = new HashMap<>(10);

    /**
     * Constructor of Board
     */
    public Board() {
        //field = new Ship[10];
    }

    /**
     * Add the Ship to the board
     *
     * @param ship a Ship object
     */
    public void addShip(Ship ship, String key) {
        /*
        field[shipsOnField] = ship;
        ship.setPosition(shipsOnField);
        shipsOnField++;
        */
        field.put(key, ship);
    }

    /**
     * @param key = the ship's position in the field ship's array
     * @param ship     = the new ship to overwrite on the previous value of field
     */
    public void modifyBoard(Ship ship, String key) {
        /*
        field[position] = ship;
         */
        field.remove(key);
        field.put(key, ship);
    }

    /**
     * Rotate a ship already placed on the board
     */
    /*
    public void rotateShipOnBoard(String key) {
        Ship shipToRotate = field.get(key);
        shipToRotate.rotateShip();
    }
    */
    /**
     * for tests
     */
    public void printConfig(){
        for(Ship i : field.values())
            System.out.println(i);
        for(String i : field.keySet())
            System.out.println(i);
    }

}
