package mapping;

import org.jetbrains.annotations.NotNull;
import tools.Coordinate;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
	int indiceminnorth;
	int indiceminsouth;
	int indiceminEast;
	int indiceminWest;

	Room(int width, int height, Coordinate position,  Random pseudoRandomList) {
		this.width = width;
		this.height = height;
		this.pseudoRandomList = pseudoRandomList;
		this.position = position ;
		map = new int[width][height];
	}

	int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public Room(@NotNull Random pseudoRandomList) {
		this.height = pseudoRandomList.nextInt();
		this.width = pseudoRandomList.nextInt();
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

	protected int NorthVoid(){
		int[] nbone = new int[width] ;
		for( int i = 0  ; i < width  ; i++ ){
			int j=1;
			while(j < height-1 && map[i][j]!= 0  ){
				j++;
			}
			nbone[i]=j;
		}
		int indicemin = 0;
		for(int i = 1; i < width - 1; i++) {
			if(nbone[i] < nbone[indicemin]) {
				indicemin = i;
			}
		}
		for (int i = 0 ; i < nbone[indicemin]+1 ; i++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[indicemin+k][i] = 0 ;
			}
		}
		return  indicemin;
	}

	protected void NorthVoid(int min){
		int j=0;
		while(j < height-1 && map[min][j]!= 0  ){
			j++;
		}
		for (int i= 0 ; i < j+1 ; i++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[min+k][i] = 0 ;
			}
		}
	}
	protected int SouthVoid(){
		int[] nbone = new int[width] ;
		for( int i = 0  ; i < width  ; i++ ){
			int j=1;
			while(j <height && map[i][height-j]!= 0 ){
				j++;
			}
			nbone[i]=j;
		}
		int minIndex = 0;
		for(int i =1 ; i<width-1 ; i++){
			if( nbone[i]<nbone[minIndex]){
				minIndex=i;
			}
		}
		for (int j= 1 ; j < nbone[minIndex]+1 ; j++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[minIndex+k][height-j] = 0 ;
			}
		}
		return minIndex ;
	}

	protected void SouthVoid(int indicemin) {
		int k = 1;
		while(k < height && map[indicemin][height - k] != 0) {
			k++;
		}
		for (int j = 0; j < k + 1; j++){
			for (int l = -1; l < 2; l++){
				map[indicemin + l][height - j] = 0 ;
			}
		}
	}

	protected void  placeRoom(Room room){
		int nsew =ThreadLocalRandom.current().nextInt(0,4);
		switch(nsew) {
			case 1:
				if (this.getNorth() != null) {
					this.getNorth().placeRoom(room);
				} else {
					room.setSouth(this);
					this.setNorth(room);
				}
				break;
			case 2:
				if (this.getSouth() != null) {
					this.getSouth().placeRoom(room);
				} else {
					room.setNorth(this);
					this.setSouth(room);
				}
				break;
			case 3:
				if (this.getEast() != null) {
					this.getEast().placeRoom(room);
				} else {
					room.setWest(this);
					this.setEast(room);
				}
				break;
			case 4:
				if (this.getWest() != null) {
					this.getWest().placeRoom(room);
				} else {
					room.setEast(this);
					this.setWest(room);
				}
				break;
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
