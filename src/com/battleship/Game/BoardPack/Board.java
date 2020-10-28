package com.battleship.Game.BoardPack;

import com.battleship.Game.ShipPack.Ship;

public class Board {

    private Ship[] field;
    private int shipsOnField = 0;


    /**
     * Constructor of Board
     */
    public Board() {
        field = new Ship[10];
    }

    /**
     * Add the Ship to the board
     *
     * @param ship a Ship object
     */
    public void addShip(Ship ship) {
        field[shipsOnField] = ship;
        ship.setPosition(shipsOnField);
        shipsOnField++;
    }

    /**
     * @param position = the ship's position in the field ship's array
     * @param ship     = the new ship to overwrite on the previous value of field
     */
    public void modifyBoard(int position, Ship ship) {
        field[position] = ship;
    }

    // TODO: Read the contents of the array "field"
}
