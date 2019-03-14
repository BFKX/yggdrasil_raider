package mapping;


import tools.Coordinate;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Room  {
    private Room north ;
    private Room south ;
    private Room east ;
    private Room west ;
    private Random pseudorendomseed  ;
    private Coordinate origine ;
    private int width ;
    private int height;
    public Room(Coordinate origine, int width , int height , Random pseudorendomseed) {
        this.origine=origine;
        this.width=width;
        this.height=height;
        this.pseudorendomseed=pseudorendomseed;
    }
    public Room(Coordinate origine , Random pseudorendomseed){
        this.origine = origine ;
        this.height = pseudorendomseed.nextInt();
        this.width = pseudorendomseed.nextInt();
    }

    public void placeRoom(Random pseudorendomseed){ // place une room a partire de la room actuel
        int localisation = pseudorendomseed.nextInt(4);
        if (localisation == 0){
            if(this.north == null ){
                int tempheight = pseudorendomseed.nextInt() ;
                this.north=new Room(this.getOrigine().sum(0,tempheight),pseudorendomseed.nextInt(),tempheight,pseudorendomseed);
                this.north.setSouth(this);
            }else {
                this.north.placeRoom(pseudorendomseed);
            }
        }
        if (localisation == 1) {
            if(this.east == null ){
                this.east=new Room(this.getOrigine().sum(this.width,0),pseudorendomseed.nextInt(),pseudorendomseed.nextInt(),pseudorendomseed);
                this.east.setWest(this);
            }else {
                this.east.placeRoom(pseudorendomseed);
            }
        }
        if (localisation == 2) {
            if(this.south == null ){
                this.south=new Room(this.getOrigine().sum(0,this.height),pseudorendomseed.nextInt(),pseudorendomseed.nextInt(),pseudorendomseed);
                this.south.setNorth(this);
            }else {
                this.south.placeRoom(pseudorendomseed);
            }
        }
        if (localisation == 3) {
            if(this.west == null ){
                int tempwidth = pseudorendomseed.nextInt() ;
                this.west=new Room(this.getOrigine().sum(-1*tempwidth,0),tempwidth,pseudorendomseed.nextInt(),pseudorendomseed);
                this.west.setEast(this);
            }else {
                this.west.placeRoom(pseudorendomseed);
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

    public Random getPseudorendomseed() {
        return pseudorendomseed;
    }

    public void setPseudorendomseed(Random pseudorendomseed) {
        this.pseudorendomseed = pseudorendomseed;
    }

    public Coordinate getOrigine() {
        return origine;
    }

    public void setOrigine(Coordinate origine) {
        this.origine = origine;
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
