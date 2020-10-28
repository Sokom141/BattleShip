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

/*
    private ShipUnit[] makeUnitList(int x1, int y1, int x2, int y2) {

        ShipUnit[] list = new ShipUnit[length];

        if (x1 - x2 == 0) { // vertical ship
            for (int i = 0; i < length; i++) {
                list[i] = new ShipUnit(x1, y1 + i);
            }
        } else {            // horizontal ship
            for (int i = 0; i < length; i++) {
                list[i] = new ShipUnit(x1 + i, y1);
            }
        }
        return list;
    }
    */
}
