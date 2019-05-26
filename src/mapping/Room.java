package mapping;

import characters.MainCharacter;
import data_structures.MonsterSet;
import tools.Coordinate;

import java.util.Random;

public abstract class Room {
	final Random pseudoRandomList;
	int[][] map;
	int width;
	int height;
	private final Coordinate position;
    private final int holeWidth = 3;
	private MonsterSet monsters;

	Room(int width, int height, Coordinate position, Random pseudoRandomList) {
		this.width = width;
		this.height = height;
		this.pseudoRandomList = pseudoRandomList;
		this.position = position;

		map = new int[width][height];
	}

	void createMonsters(MainCharacter mainCharacter) {
		this.monsters = new MonsterSet(10, mainCharacter, this.map);
		this.monsters.setStartMonster(this, mainCharacter.getPosition());
	}

	void applyFiltering(int[][] mapFiltering) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++) {
				if (mapFiltering[i][j] >= 6)
					map[i][j] = 1;
				if (mapFiltering[i][j] < 6)
					map[i][j] = 0;
			}
	}

	int[][] fullRangeFiltering(int n) {
		int[][] temp = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int sum = 0;
				for (int l = -n; l < n + 1; l++)
					for (int k = -n; k < n + 1; k++)
						if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {
							sum = sum + map[i + l][j + k];
						} else {
							sum = 25;
							break;
						}
				temp[i][j] = sum;
			}
		}
		return temp;
	}

    abstract void placeWall();

	abstract void delete25(int range);

	void northVoid(int min){
		int j = 0;
		while(j + 4 < height - 1 && map[min][j] > 0 && !( map[min][j + 1] <= 0 &&
				map[min][j + 2] <= 0 && map[min][j + 3] <= 0 && map[min][j + 4] <= 0))
			j++;
		for (int i= 0; i < j+1; i++)
			for (int k = -holeWidth; k < holeWidth + 1; k++)
				map[min + k][i] = 0;
	}

	void southVoid(int min){
		int j = height - 1;
		while(j - 4 > 0 && map[min][j] > 0 && !(map[min][j - 1] <= 0 && map[min][j - 2] <= 0 && map[min][j - 3] <= 0
                && map[min][j - 4] <= 0))
			j--;
		for (int i = height - 1; i >= j - 1; i--)
			for (int k = -holeWidth; k< holeWidth +1; k++ )
				map[min + k][i] = 0;
	}

	void westVoid(int min){
		int j = 0;
		while(j + 4 < width - 1 && map[j][min] > 0 && !(map[j + 1][min] <= 0 &&  map[j + 2][min] <= 0
                && map[j + 3][min] <= 0 &&  map[j + 4][min] <= 0))
			j++;
		for (int i = 0; i < j + 1; i++)
			for (int k = -holeWidth; k < holeWidth + 1; k++)
				map[i][min + k] = 0;
	}

	void eastVoid(int min){
		int j = width - 1;
		while(j - 4 > 1 && map[j][min] > 0 && !(map[j - 1][min] <= 0 && map[j - 2][min] <= 0 && map[j - 3][min] <= 0
				&& map[j - 4][min] <= 0))
			j--;
		for (int i = width - 1; i >= j - 1; i--)
			for (int k = -holeWidth; k < holeWidth + 1; k++)
				map[i][min + k] = 0;
	}

	abstract void addGroundVariation(int [] seed);

	public int[][] getMap() {
		return map;
	}

	Coordinate getPosition() {
		return position;
	}

	public MonsterSet getMonsters() {
		return monsters;
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}
}
