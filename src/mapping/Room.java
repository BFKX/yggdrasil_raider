package mapping;


import tools.Coordinate;

import java.util.Random;

public class Room  {
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    protected Random pseudoRandomList;
    private Coordinate origin;
    private int width;
    private int height;

    public Room(Coordinate origin, int width, int height, Random pseudoRandomList) {
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.pseudoRandomList = pseudoRandomList;
    }

    public Room(Coordinate origin, Random pseudoRandomList){
        this.origin = origin;
        this.height = pseudoRandomList.nextInt();
        this.width = pseudoRandomList.nextInt();
    }

    public void placeRoom(Random pseudoRandomSeed){ // place une room a partire de la room actuel
        int location = pseudoRandomSeed.nextInt(4);
        if (location == 0){
            if(this.north == null ){
                int tempheight = pseudoRandomSeed.nextInt() ;
                this.north=new Room(this.getOrigin().sum(0,tempheight),pseudoRandomSeed.nextInt(),tempheight,pseudoRandomSeed);
                this.north.setSouth(this);
            }else {
                this.north.placeRoom(pseudoRandomSeed);
            }
        }
        if (location == 1) {
            if(this.east == null ){
                this.east=new Room(this.getOrigin().sum(this.width,0),pseudoRandomSeed.nextInt(),pseudoRandomSeed.nextInt(),pseudoRandomSeed);
                this.east.setWest(this);
            }else {
                this.east.placeRoom(pseudoRandomSeed);
            }
        }
        if (location == 2) {
            if(this.south == null ){
                this.south=new Room(this.getOrigin().sum(0,this.height),pseudoRandomSeed.nextInt(),pseudoRandomSeed.nextInt(),pseudoRandomSeed);
                this.south.setNorth(this);
            }else {
                this.south.placeRoom(pseudoRandomSeed);
            }
        }
        if (location == 3) {
            if(this.west == null ){
                int tempwidth = pseudoRandomSeed.nextInt() ;
                this.west=new Room(this.getOrigin().sum(-1*tempwidth,0),tempwidth,pseudoRandomSeed.nextInt(),pseudoRandomSeed);
                this.west.setEast(this);
            }else {
                this.west.placeRoom(pseudoRandomSeed);
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

    public Coordinate getOrigin() {
        return origin;
    }

    public void setOrigin(Coordinate origin) {
        this.origin = origin;
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
