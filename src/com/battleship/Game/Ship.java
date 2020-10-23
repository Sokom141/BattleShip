package com.battleship.Game;

public class Ship {

    private ShipUnit[] unitList;

    public Ship(int x1, int y1, int x2, int y2){

        unitList = makeUnitList(x1,y1,x2,y2);

    }

    public boolean isALive(){

        for(ShipUnit unit : unitList){

            if(unit.isAlive()){
                return true;
            }
        }
        return false;
    }

    private ShipUnit[] makeUnitList(int x1,int y1, int x2, int y2){

        ShipUnit[] list = new ShipUnit[x1 - x2 + y1 - y2];
        if(x1-x2 == 0){ // vertical ship
            for(int i=0; i<y2-y1; i++){
                list[i] = new ShipUnit(x1,y1+i);
            }
        } else { //horizzontal ship
            for(int i=0; i<x2-x1; i++){
                list[i] = new ShipUnit(x1+i,y1);
            }
        }
        return list;
    }   
    
}

