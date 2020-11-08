package com.battleship.game.shippack;

class ShipUnit {

    private int xAxis;
    private int yAxis;
    private boolean alive;

    /**
     * ShipUnit constructor
     *
     * @param i = coordinate on x axis
     * @param j = coordinate on y axis
     */
    public ShipUnit(int i, int j) {
        xAxis = i;
        yAxis = j;
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
        return new int[]{xAxis, yAxis};
    }

    /**
     * Set the x coordinate
     *
     * @param n an integer between 0 and 9
     */
    public void setX(int n) {
        xAxis = n;
    }

    /**
     * Set the y coordinate
     *
     * @param n an integer between 0 and 9
     */
    public void setY(int n) {
        yAxis = n;
    }

    /**
     * Destroy the ShipUnit component
     */
    public void destroy() {
        alive = false;
    }

}