package mapping;

import characters.MonsterSet;
import tools.Coordinate;

import java.util.ArrayList;
import java.util.Random;

class Cave extends Room {
	private final ArrayList< Integer> possibleValues = new ArrayList<>();
	Cave(int width, int height, Random pseudoRandomList, Coordinate position) {
		super(width, height,position,pseudoRandomList);
		initPossibleValues();
		int fillPercentage = 60;
		this.width = width;
		this.height = height;
		this.map = new int[width][height];
		randomFill(fillPercentage);
		for (int l = 0; l < 2; l++) {
			for (int k = 0; k < 2; k++) {
				applyFiltering(fullRangeFiltering(1), 6);
			}
			for (int i = 0; i < 10; i++) {
				additiveFiltering();
			}
			applyFiltering(fullRangeFiltering(1), 6);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i ==0 || i == width - 1 || j ==0|| j ==height-1 ) {
					map[i][j] = 1;
				}
			}
		}
		int[][] f1 = fullRangeFiltering(20) ;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (f1[i][j] == 0) {
					map[i][j]= 1;
				}
			}
		}
		delete25(1);
		applyFiltering(fullRangeFiltering(1), 6);
	}
	public void addGroundVariation(int[] seeds, int limit) {
		Coordinate[] seedsCoordinates = new Coordinate[seeds.length];
		for (int i = 0; i < seeds.length; i++) {
			int x = pseudoRandomList.nextInt(width);
			int y = pseudoRandomList.nextInt(height);
			seedsCoordinates[i] = new Coordinate(x, y);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j] == 0) {
					Coordinate ij = new Coordinate(i, j);
					int k = 0;
					for (Coordinate c : seedsCoordinates) {
						double d = Math.pow(c.distance(ij), 2);
						double u1 = pseudoRandomList.nextDouble()  ;
						double u2 = pseudoRandomList.nextDouble();
						double nb = Math.sqrt((-2)*Math.log(u1))*Math.cos(u2); // gausien centrer en 0 de'ecartipe 1

						nb = Math.abs(nb)*d/900;
						if (nb < 4  ){
							map[i][j] = seeds[k] - (3-(int)(nb));
						}
						k++;
					}
				}
			}
		}
	}
	private void initPossibleValues(){
		 int[] values = {0, 1, 2, 3, 4, 6, 8, 9, 12, 13, 15, 17, 18, 19, 21, 41, 163};
		 for ( int i : values) {
			 possibleValues.add(i);
		 }
	}
	private void delete25(int range) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!(possibleValues.contains(map[i][j]))) {
					for (int k = -range; k < range + 1; k++) {
						for (int l = -range; l < range + 1; l++) {
							if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {
								if (pseudoRandomList.nextInt(1) < 0.6) {
									map[i + l][j + k] = 0;
								}
							}

						}
					}
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j] > 0) {
					map[i][j] = 1;
				}
			}
		}
	}

	private void randomFill(int fillPercentage) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i <= 4 || i > width - 4 || j <= 4 || j >= height - 4) {
					map[i][j] = 1;
				} else {
					map[i][j] = pseudoRandomList.nextInt(100) < fillPercentage ? 1 : 0;
				}
			}
		}
	}

	public void placeWall() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j] != 0) {
					if (j - 1 > 0 && map[i][j - 1] == 0) { // North
						map[i][j] = map[i][j] + 1;
					}
					if (i - 1 > 0 && map[i - 1][j] == 0) { // West
						map[i][j] = map[i][j] + 2;
					}
					if (j + 1 < this.height && map[i][j + 1] == 0) { // South
						map[i][j] = map[i][j] + 5;
					}
					if (i + 1 < this.width && map[i + 1][j] == 0) { // East
						map[i][j] = map[i][j] + 11;
					}
					if (map[i][j] == 1) {
						if (j - 1 > 0 && i - 1 > 0 && map[i - 1][j - 1] == 0) { // North West
							map[i][j] = map[i][j] + 20;
						}
						if (i - 1 > 0 && j + 1 < this.height && map[i - 1][j + 1] == 0) { // South West
							map[i][j] = map[i][j] + 40;
						}
						if (j + 1 < this.height && i + 1 < this.width && map[i + 1][j + 1] == 0) { // South East
							map[i][j] = map[i][j] + 81;
						}
						if (i + 1 < this.width && j - 1 > 0 && map[i + 1][j - 1] == 0) { // North East
							map[i][j] = map[i][j] + 162;
						}
					}
				}
			}
		}
	}

	private void additiveFiltering() {
		int[][] temp = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int sum = 0;
				for (int l = -2; l < 3; l++) {
					for (int k = -2; k < 3; k++) {
						if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {
							sum = sum + map[i + l][j + k];
						}
					}
				}
				if (sum > 14) {
					temp[i][j] = 1;
				} else if (sum < 12) {
					temp[i][j] = 0;
				} else {
					temp[i][j] = map[i][j];
				}
			}
		}
		for (int i = 0; i < width; i++) {
			if (height >= 0)
				System.arraycopy(temp[i], 0, map[i], 0, height);
		}
	}

	public void coloring() {
		int setValue = -1;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j] == 0) {
					if (detection(i, j, setValue, 0, 0, 5)) {
						replacement(i, j, 0, 25);
					}
					setValue--;
				}
			}
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j] < 0) {
					map[i][j] = 0;
				}
			}
		}

	}

	private boolean detection(int i, int j, int setValue, int access, int counter, int limit) {
		map[i][j] = setValue;
		counter++;
		if (counter < limit) {
			for (int l = -1; l < 2; l++) {
				for (int k = -1; k < 2; k++) {
					if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
						if (map[i + l][j + k] <= access) {
							if (!(detection(i + l, j + k, setValue, access, counter, limit))) {
								return false;
							}
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private void replacement(int i, int j, int origin, int end) {
		map[i][j] = end;
		for (int l = -1; l < 2; l++) {
			for (int k = -1; k < 2; k++) {
				if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
					if (map[i + l][j + k] == origin) {
						replacement(i + l, j + k, origin, end);
					}
				}
			}
		}
	}
}
