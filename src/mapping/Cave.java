package mapping;
import java.util.Random;

public class Cave extends Room {
    public Cave(int width, int height, Random pseudoRandomList) {
        super(width, height, pseudoRandomList);
        //int fillPercentage = ThreadLocalRandom.current().nextInt(43, 47);
        int fillPercentage = 62;
        this.width = width;
        this.height = height;
        this.map = new int[width][height];
        randomFill(fillPercentage);
        /*for (int k = 0; k < 2; k++) { // filter melange range 1 et 2
            int[][] f1 = fullnRangefiltering(1);
            int[][] f2 = fullnRangefiltering(2);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (  i  != 0 && i !=width -1 && j!= 0 && j!= height-1 ) {
                        if (f1[i][j] >= 5 || f2[i][j] <= 1) {
                            map[i][j] = 1;
                        } else {
                            map[i][j] = 0;
                        }
                    }
                }
            }
        }*/
        for (int l=0 ; l < 2 ; l++ ) {
            for (int k = 0; k < 2; k++) {
                applyfiltering(fullnRangefiltering(1), 6);
            }
            for (int i = 0; i < 10; i++) {
                additiveFiltering();
            }
            applyfiltering(fullnRangefiltering(1), 6);
        }
        for ( int k = 0 ; k < 3  ; k++ ) {
            placeWall();
            delete25(1);
            placeWall();
        }

    }

    private void delete25(int range) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j] == 25 ) {
                    for ( int k = -range ; k < range +1  ; k++  ){
                        for ( int l = -range ; l < range +1 ; l ++ ){
                            map[i+k] [j+l] = 0 ;
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



    /**
     * generation d'un bruit
     *
     * @param fillPercentage
     */
    @SuppressWarnings("JavaDoc")
    private void randomFill(int fillPercentage) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i <=4 || i > width - 4 || j <= 4 || j >= height -4 ) {
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

    /**
     * place walls
     */
    private void placeWall(){
        for ( int i = 0 ; i<width ;i++){
            for ( int j =0 ;  j < height ; j++){
                if (map[i][j]!=0 ) {
                    if (j-1>0 && map[i][j-1] == 0){ //north
                        map[i][j] = map[i][j]+1 ;
                    }
                    if (i-1>0 && map[i-1][j] == 0){ //east
                        map[i][j] = map[i][j] +2 ;
                    }
                    if ( j+1 < this.height && map[i][j+1] == 0){//south
                        map[i][j] = map[i][j]+5 ;
                    }
                    if ( i+1 <this.width && map[i+1][j] == 0){//east
                        map[i][j] = map[i][j]+11 ;
                    }
                    if(map[i][j]==1){
                        if (j-1>0 && i-1>0 && map[i-1][j-1] == 0){ //North West
                            map[i][j] = map[i][j]+20 ;
                        }
                        if (i-1>0 && j+1<this.height &&map[i-1][j+1] == 0){ //South West
                            map[i][j] = map[i][j] +40 ;
                        }
                        if ( j+1 < this.height &&  i+1 < this.width && map[i+1][j+1] == 0){//South East
                            map[i][j] = map[i][j]+81 ;
                        }
                        if ( i+1 <this.width && j-1 >0 && map[i+1][j-1] == 0){//North East
                            map[i][j] = map[i][j]+162 ;
                        }
                    }
                }
            }
        }
    }

    private void additiveFiltering(){
        int [][] temp = new int [width] [height] ;
        for ( int i = 0 ; i<width ;i++) {
            for (int j = 0; j < height ; j++) {
                    int sum = 0;
                    for (int l = -2; l < 3; l++) {
                        for (int k = -2; k < 3; k++) {
                            if (i + l < width && j + k >= 0 && j + k < height && i + l>= 0 ) {
                                sum=sum+ map[i+l][j+k];
                            }
                        }
                    }
                    if(sum>14) {
                        temp[i][j] = 1 ;
                    }else if(sum < 12 ) {
                        temp[i][j] = 0;
                    }else {
                        temp[i][j]= map[i][j];
                    }
                }
            }
        for ( int i = 0 ; i<width;i++) {
            if (height >= 0) System.arraycopy(temp[i], 0, map[i], 0, height);
        }
    }

    public void coloring(){
        int setValue = -1 ;
        for (int i= 0 ; i < width ; i++) {
            for(int j =0 ; j< height ; j++ ) {
                if(map[i][j] == 0 ){
                    if (detection( i,j,setValue,0,0,5)) {
                        remplacement(i,j,0,25);
                    }
                    setValue -- ;
                }
            }
        }
        for (int i= 0 ; i < width ; i++) {
            for (int j = 0; j < height; j++) {
                if(map[i][j] < 0  ){
                    map[i][j] = 0 ;
                }
            }
        }

    }

    private boolean detection(int i, int j, int setValue, int access, int counter, int limit) {
        map[i][j] = setValue ;
        counter++;
        if (counter < limit) {
            for (int l = -1; l < 2; l++) {
                for (int k = -1; k < 2; k++) {
                    if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
                        if (map[i + l][j + k]  <= access) {
                            if (!(detection(i + l, j + k, setValue, access, counter, limit))){
                                return false ;
                            }
                        }
                    }
                }
            }
            return true;
        }else{
            return false;
        }
    }
    private void remplacement(int i, int j, int origin, int end) {
        map[i][j] = end;
        for (int l = -1; l < 2; l++) {
            for (int k = -1; k < 2; k++) {
                if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
                    if (map[i + l][j + k] == origin) {
                        remplacement(i+l,j+k,origin,end);
                    }
                }
            }
        }
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
