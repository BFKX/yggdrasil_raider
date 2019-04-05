package mapping;


import tools.Coordinate;

import java.util.ArrayList;
import java.util.Random;

public class Room  {
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    protected Random pseudoRandomList;
    protected int[][] map ;
    protected int width;
    protected int height;

    public Room(int width, int height, Random pseudoRandomList) {
        this.width = width;
        this.height = height;
        this.pseudoRandomList = pseudoRandomList;
        map = new int[width][height];
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

    public ArrayList<Integer> hadNorthWay(){
        ArrayList <Integer> temp =  new ArrayList<Integer>();
        for ( int i = 0 ; i<width ; i++ ){
            if ( map[i][0] == 0 ){
                temp.add(i);
            }
        }
        return temp;
    }
    public ArrayList <Integer> hadSouthWay(){
        ArrayList <Integer> temp =  new ArrayList<Integer>();
        for ( int i = 0 ; i<width ; i++ ){
            if ( map[i][height-1] == 0 ){
                temp.add(i); ;
            }
        }
        return temp;
    }
    public ArrayList <Integer> hadEastWay(){
        ArrayList <Integer> temp =  new ArrayList<Integer>();
        for ( int i = 0 ; i<height ; i++ ){
            if ( map[0][i] == 0 ){
                temp.add(i);
            }
        }
        return temp;
    }
    public ArrayList <Integer> hadWestWay(){
        ArrayList <Integer> temp =  new ArrayList<Integer>();
        for ( int i = 0 ; i<height ; i++ ){
            if ( map[width-1][i] == 0 ){
                temp.add(i); ;
            }
        }
        return temp;
    }
    public void applyfiltering(int[][] mapfiltrering, int limit) { //appliquelefiltre
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (mapfiltrering[i][j] >=limit) {
                    map[i][j] = 1;
                }
                if (mapfiltrering[i][j] < limit) {
                    map[i][j] = 0;
                }
            }
        }
    }


    public int[][] fullnRangefiltering(int n) {
        int[][] temp = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int sum = 0;
                for (int l = -n; l < n + 1; l++) {
                    for (int k = -n; k < n + 1; k++) {
                        if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {
                            sum = sum + map[i + l][j + k];
                        } else {
                            sum = 25;
                            break;
                        }
                    }
                }
                temp[i][j] = sum;
            }
        }
        return temp;
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
