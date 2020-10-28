package com.battleship.Game.ShipPack;

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

    public void setX(int n) {
        xAxis = n;
    }

    public void setY(int n) {
        yAxis = n;
    }

    public void destroy() {
        alive = false;
    }

}