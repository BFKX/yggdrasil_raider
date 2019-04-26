package mapping;

import java.util.ArrayList;
import java.util.Random;

public class Cave extends Room {
	ArrayList< Integer> posibleValues = new ArrayList<Integer>();
	Cave(int width, int height, Random pseudoRandomList) {
		super(width, height, pseudoRandomList);
		initposibleValues();
		// int fillPercentage = ThreadLocalRandom.current().nextInt(43, 47);
		int fillPercentage = 62;
		this.width = width;
		this.height = height;
		this.map = new int[width][height];
		randomFill(fillPercentage);
		/*
		 * for (int k = 0; k < 2; k++) { // filter melange range 1 et 2 int[][] f1 =
		 * fullnRangeFiltering(1); int[][] f2 = fullnRangeFiltering(2); for (int i = 0;
		 * i < width; i++) { for (int j = 0; j < height; j++) { if ( i != 0 && i !=width
		 * -1 && j!= 0 && j!= height-1 ) { if (f1[i][j] >= 5 || f2[i][j] <= 1) {
		 * map[i][j] = 1; } else { map[i][j] = 0; } } } } }
		 */
		for (int l = 0; l < 2; l++) {
			for (int k = 0; k < 2; k++) {
				applyFiltering(fullnRangefiltering(1), 6);
			}
			for (int i = 0; i < 10; i++) {
				additiveFiltering();
			}
			applyFiltering(fullnRangefiltering(1), 6);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i ==0 || i == width - 1 || j ==0|| j ==height-1 ) {
					map[i][j] = 1;
				}
			}
		}
		int[][] f1 = fullnRangefiltering(20) ;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (f1[i][j] == 0) {
					map[i][j]= 1;
				}
			}
		}
		placeWall();
		delete25(1);
		applyFiltering(fullnRangefiltering(1), 6);
		NorthVoid();
		SouthVoid();
		placeWall();
	}

	private void initposibleValues(){
		 int[] values = {0,1,2,3,4,6,8,9,12,13,15,17,18,19, 21, 41, 163};
		 for ( int i : values) {
			 posibleValues.add(i);
		 }
	}
	private void delete25(int range) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!(posibleValues.contains( map[i][j]))){
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
					if (pseudoRandomList.nextInt(100) < fillPercentage) {
						map[i][j] = 1;
					} else {
						map[i][j] = 0;
					}
				}
			}
		}
	}

	private void placeWall() {
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


	private void NorthVoid(){
		int[] nbone = new int[width] ;
		for( int i = 0  ; i < width  ; i++ ){
			int j=1;
			while(j < height-1 && map[i][j]!= 0  ){
				j++;
			}
			nbone[i]=j;
		}
		int indicemin = 0;
		for(int i =1 ; i<width-1 ; i++){
			if( nbone[i]<nbone[indicemin]){
				indicemin=i;
			}
		}
		for (int i= 0 ; i < nbone[indicemin]+1 ; i++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[indicemin+k][i] = 0 ;
			}
		}
	}
	private void NorthVoid(int min){
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
	private void SouthVoid(){
		int[] nbone = new int[width] ;
		for( int i = 0  ; i < width  ; i++ ){
			int j=1;
			while(j <height && map[i][height-j]!= 0 ){
				j++;
			}
			nbone[i]=j;
		}
		int indicemin = 0;
		for(int i =1 ; i<width-1 ; i++){
			if( nbone[i]<nbone[indicemin]){
				indicemin=i;
			}
		}
		for (int j= 1 ; j < nbone[indicemin]+1 ; j++){
			for ( int k = -1 ; k<2 ; k++ ){
				map[indicemin+k][height-j] = 0 ;
			}
		}
	}

	private void SouthVoid(int indicemin) {
		int k=1;
		while(k <height && map[indicemin][height-k]!= 0 ){
			k++;
		}
		for (int j= 0 ; j < k+1 ; j++){
			for ( int l = -1 ; l<2 ; l++ ){
				map[indicemin+l][height-j] = 0 ;
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
