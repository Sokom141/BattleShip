package com.battleship.Game.Ship;

class ShipUnit {

    private int x_axis;
    private int y_axis;
    private boolean alive;

    /**
     * ShipUnit constructor
     *
     * @param i = coordinate on x axis
     * @param j = coordinate on y axis
     */
    public ShipUnit(int i, int j) {
        x_axis = i;
        y_axis = j;
        alive = true;
    }

    /**
     * @return alive attribute
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * @return x axis and y axis
     */
    public int[] coordinates() {
        return new int[]{x_axis, y_axis};
    }

}