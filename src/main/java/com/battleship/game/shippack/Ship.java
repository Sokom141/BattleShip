package com.battleship.game.shippack;

public class Ship {

    private final ShipUnit[] unitList;
    private final int length;
    private final int[] head_coordinates;
    private final int[] tail_coordinates;
    private final boolean orientation; //FINAL?
    public int position; //position in class Board

    /**
     * Constructor for a generic ship.
     *
     * @param x1 = the head x coordinate.
     * @param y1 = the head y coordinate.
     * @param x2 = the tail x coordinate.
     * @param y2 = the tail y coordinate.
     */
    public Ship(int x1, int y1, int x2, int y2) {

        head_coordinates = new int[]{x1,y1};
        tail_coordinates = new int[]{x2,y2};
        length = (x2 - x1 == 0) ? y2 - y1 : x2 - x1;
        orientation = x2 - x1 == 0; // true = vertical , false = horizontal
        unitList = makeUnitList(x1, y1, x2, y2);
        position = 0;
    }

    /**
     * @return ShipTypeItem.head_coordinates
     */
    public int[] getHeadCoordinates() {
        return head_coordinates;
    }

    /**
     * @return ShipTypeItem.tail_coordinates
     */
    public int[] getTailCoordinates() {
        return tail_coordinates;
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

    public boolean isVertical(){
        return orientation;
    }

    /**
     * @param n the index of the ShipUnit
     * @return the ShipUnit chosen
     */
    public ShipUnit getUnit(int n) {
        return unitList[n];
    }

    /**
     * @return the length of the Ship
     */
    public int getLength() {

        return length;
    }

    public void setPosition(int n) {
        position = n;
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
        } else {            // horizontal ship
            for (int i = 0; i < length; i++) {
                list[i] = new ShipUnit(x1 + i, y1);
            }
        }
        return list;
    }

    /**
     * Method to rotate the Ship on the board
     */
    public void rotateShip() {

        if (head_coordinates[0] == tail_coordinates[0]) {
            tail_coordinates[0] += this.length;
        } else {
            tail_coordinates[1] += this.length;
        }
    }

    public String toString(){
        String str = "head: x=" + head_coordinates[0] +" y="+ head_coordinates[1];
        str += "\ntail: x=" + tail_coordinates[0] +" y="+ tail_coordinates[1];
        return str;
    }
}

