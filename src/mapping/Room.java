package mapping;

import org.jetbrains.annotations.NotNull;

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

	Room(int width, int height, Random pseudoRandomList) {
		this.width = width;
		this.height = height;
		this.pseudoRandomList = pseudoRandomList;
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

	Room getNorth() {
		return north;
	}

	void setNorth(Room north) {
		this.north = north;
	}

	Room getSouth() {
		return south;
	}

	private void setSouth(Room south) {
		this.south = south;
	}

	Room getEast() {
		return east;
	}

	private void setEast(Room east) {
		this.east = east;
	}

	Room getWest() {
		return west;
	}

	private void setWest(Room west) {
		this.west = west;
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	private void  placeRoom(Room room){
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
}
