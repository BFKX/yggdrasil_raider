package mapping;


import tools.Coordinate;

import java.util.Random;

public class Room  {
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    protected Random pseudoRandomList;
    private int[][] map ;
    private int width;
    private int height;

    public Room(int width, int height, Random pseudoRandomList) {
        this.width = width;
        this.height = height;
        this.pseudoRandomList = pseudoRandomList;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }
    public Room(Coordinate origin, Random pseudoRandomList){
        this.height = pseudoRandomList.nextInt();
        this.width = pseudoRandomList.nextInt();
    }

    public void placeRoom(Random pseudoRandomSeed,Room room){ // place une room a partire de la room actuel
        int location = pseudoRandomSeed.nextInt(4);
        if (location == 0){
            if(this.north == null ){
                this.north.setSouth(room);
            }else {
                this.north.placeRoom(pseudoRandomSeed,room);
            }
        }
        if (location == 1) {
            if(this.east == null ){
                this.east.setWest(room);
            }else {
                this.east.placeRoom(pseudoRandomSeed,room);
            }
        }
        if (location == 2) {
            if(this.south == null ){
                this.south.setNorth(room);
            }else {
                this.south.placeRoom(pseudoRandomSeed,room);
            }
        }
        if (location == 3) {
            if(this.west == null ){
                int tempwidth = pseudoRandomSeed.nextInt() ;
               this.west.setEast(room);
            }else {
                this.west.placeRoom(pseudoRandomSeed,room);
            }
        }
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Random getPseudoRandomList() {
        return pseudoRandomList;
    }

    public void setPseudoRandomList(Random pseudoRandomList) {
        this.pseudoRandomList = pseudoRandomList;
    }



    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
