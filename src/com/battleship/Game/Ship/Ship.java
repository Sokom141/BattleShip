package com.battleship.Game.Ship;

public class Ship {

    private final ShipUnit[] unitList;
    private final int length;

    /**
     * Constructor for a generic ship.
     *
     * @param x1 = the head x coordinate.
     * @param y1 = the head y coordinate.
     * @param x2 = the tail x coordinate.
     * @param y2 = the tail y coordinate.
     */
    public Ship(int x1, int y1, int x2, int y2) {

        length = (x2 - x1 == 0) ? y2 - y1 : x2 - x1;
        unitList = makeUnitList(x1, y1, x2, y2);
    }

    /**
     * Checks if at least one unit component
     * of the ship is alive.
     *
     * @return True if the former condition is satisfied.
     */
    public boolean isALive() {

        for (ShipUnit unit : unitList) {

            if (unit.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {

        return length;
    }

    /**
     * Private method that construct the internal state of the ship.
     *
     * @param x1 = the head x coordinate.
     * @param y1 = the head y coordinate.
     * @param x2 = the tail x coordinate.
     * @param y2 = the tail y coordinate.
     * @return an array containing the ShipUnit elements.
     */
    private ShipUnit[] makeUnitList(int x1, int y1, int x2, int y2) {

        ShipUnit[] list = new ShipUnit[length];

        if (x1 - x2 == 0) { // vertical ship
            for (int i = 0; i < length; i++) {
                list[i] = new ShipUnit(x1, y1 + i);
            }
        } else { // horizontal ship
            for (int i = 0; i < length; i++) {
                list[i] = new ShipUnit(x1 + i, y1);
            }
        }
        return list;
    }

}

