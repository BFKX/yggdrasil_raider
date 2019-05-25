package mapping;

import characters.MainCharacter;
import characters.MonsterSet;
import tools.Coordinate;

import java.util.Random;

public abstract class Room {
	private MainCharacter mainCharacter;
	private Room north;
	private Room south;
	private Room east;
	private Room west;
	Random pseudoRandomList;
	int[][] map;
	int width;
	int height;
	private Coordinate position;
	int indiceminNorth;
	int indiceminSouth;
	int indiceminEast;
	int indiceminWest;
    int largeur = 3;
	private MonsterSet monsters;
	Room(int width, int height, Coordinate position, Random pseudoRandomList) {
		this.width = width;
		this.height = height;
		this.pseudoRandomList = pseudoRandomList;
		this.position = position ;
		map = new int[width][height];
		indiceminNorth = -1;
		indiceminEast = - 1;
		indiceminWest= - 1;
		indiceminSouth = - 1;
	}
	public void setMainCharacter(MainCharacter mainCharacter){
		this.mainCharacter=mainCharacter;
	}

	public void createMonsters(MainCharacter mainCharacter) {
		this.monsters = new MonsterSet(10,mainCharacter,this.map);
		this.monsters.setStartMonster(this,mainCharacter.getPosition());
	}

	public int[][] getMap() {
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

    abstract void placeWall();

	abstract void delete25(int range);

	public void northVoid(int min){
		int j=0;
		while( j+4 < height-1 && map[min][j] > 0 && !( map[min][j+1] <= 0 && map[min][j+2] <= 0 && map[min][j+3] <= 0
                && map[min][j+4] <= 0)){
			j++;
		}
		for (int i= 0 ; i < j+1 ; i++){
			for ( int k = -largeur ; k<largeur+1 ; k++ ){
				map[min+k][i] = 0 ;
			}
		}
	}

	public void southVoid(int min){
		int j=height-1;
		while(j-4 > 0 && map[min][j] > 0 && !(map[min][j-1] <= 0 && map[min][j-2] <= 0 && map[min][j-3] <= 0
                && map[min][j-4] <= 0 ) ){
			j--;
		}
		for (int i= height-1 ; i >= j-1 ; i--){
			for ( int k = -largeur ; k<largeur+1 ; k++ ){
				map[min+k][i] = 0 ;
			}
		}
	}

	public void westVoid(int min){
		int j=0;
		while(j +4< width-1 && map[j][min] > 0 && !( map[j+1][min] <= 0 &&  map[j+2][min] <= 0
                && map[j+3][min] <= 0 &&  map[j+4][min] <= 0) ){
			j++;
		}
		for (int i= 0 ; i < j+1 ; i++){
			for ( int k = -largeur ; k<largeur+1 ; k++ ){
				map[i][min+k] = 0 ;
			}
		}
	}
    public abstract void addGroundVariation(int [] seed, int i );
	public void eastVoid(int min){
		int j=width-1;
		while(j-4 >1 && map[j][min] > 0 && ! ( map[j-1][min] <= 0 && map[j-2][min] <= 0 && map[j-3][min] <= 0
                && map[j-4][min] <= 0 ) ){
			j--;
		}
		for (int i= width-1 ; i >= j-1 ; i--){
			for ( int k = -largeur ; k<largeur+1 ; k++ ){
				map[i][min+k] = 0 ;
			}
		}
	}


	public void setNorth(Room north) {
		this.north = north;
	}


	public void setSouth(Room south) {
		this.south = south;
	}


	public void setEast(Room east) {
		this.east = east;
	}


	public void setWest(Room west) {
		this.west = west;
	}


	public Coordinate getPosition() {
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
