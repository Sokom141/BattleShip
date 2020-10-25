package com.battleship.Game.ShipPack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ShipUnitTest {

    private ShipUnit ShipUnitTest = new ShipUnit(0, 0);

    @Test
    void isAlive() {
        Assertions.assertTrue(ShipUnitTest.isAlive());
    }

    @Test
    void coordinates() {
        int[] coords = new int[]{0, 0};
        Assertions.assertArrayEquals(coords, ShipUnitTest.coordinates());
    }

    @Test
    void setX() {
        int x = 5;
        int[] coords = new int[]{x, 0};
        ShipUnitTest.setX(x);
        Assertions.assertArrayEquals(coords, ShipUnitTest.coordinates());
    }

    @Test
    void setY() {
        int y = 7;
        int[] coords = new int[]{0, y};
        ShipUnitTest.setY(y);
        Assertions.assertArrayEquals(coords, ShipUnitTest.coordinates());
    }

    @Test
    void destroy() {
        ShipUnitTest.destroy();
        Assertions.assertFalse(ShipUnitTest.isAlive());
    }
}