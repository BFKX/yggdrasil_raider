package mapping;

import tools.Coordinate;

import java.util.Random;

public class Room {
	private Room north;
	private Room south;
	private Room east;
	private Room west;
	Random pseudoRandomList;
	int[][] map;
	int width;
	int height;
	private Coordinate position ;
	int indiceminNorth;
	int indiceminSouth;
	int indiceminEast;
	int indiceminWest;

	Room(int width, int height, Coordinate position,  Random pseudoRandomList) {
		this.width = width;
		this.height = height;
		this.pseudoRandomList = pseudoRandomList;
		this.position = position ;
		map = new int[width][height];
		indiceminNorth = -1;
		indiceminEast=-1;
		indiceminWest= -1;
		indiceminSouth=-1;
	}

	int[][] getMap() {
		return map;
	}

	void applyFiltering(int[][] mapFiltering, int limit) {
		for (int i = 0; i < width ; i++) {
			for (int j = 0; j < height ; j++) {
				if (mapFiltering[i][j] >= limit) {
					map[i][j] = 1;
				}
				if (mapFiltering[i][j] < limit) {
					map[i][j] = 0;
				}
			}
		}
	}

	int[][] fullRangeFiltering(int n) {
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


	public void northVoid(int min){
		int j=0;
		while(j < height-1 && map[min][j]< 0  ){
			j++;
		}
		for (int i= 0 ; i < j+1 ; i++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[min+k][i] = -800 ;
			}
		}
	}
	public void southVoid(int min){
		int j=height-1;
		while(j > 0 && map[min][j] < 0  ){
			j--;
		}
		for (int i= height-1 ; i >= j-1 ; i--){
			for ( int k = -1 ; k<2 ; k++ ){
				map[min+k][i] = -800 ;
			}
		}
	}
	public void westVoid(int min){
		int j=0;
		while(j < width-1 && map[j][min]< 0  ){
			j++;
		}
		for (int i= 0 ; i < j+1 ; i++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[i][min+k] = -800 ;
			}
		}
	}
	public void eastVoid(int min){
		int j=width-1;
		while(j >1 && map[j][min] < 0  ){
			j--;
		}
		for (int i= width-1 ; i >= j-1 ; i--){
			for ( int k = -1 ; k<2 ; k++ ){
				map[i][min+k] = -800 ;
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

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

}
