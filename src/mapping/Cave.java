package mapping;
import java.util.Random;

public class Cave extends Random {
    private int [] [] mapcave  ;
    private int width ;
    private int height ;
    public Cave() {}
    public Cave (int width , int height) {
        this.width=width;
        this.height=height;
        this.mapcave = new int[width][height] ;
    }
    public void randomFill(long seed , int fillPurcentage) {
        Random pseudorendomseed = new Random(seed);  //  crée une distribution aléatroi qui depend de la seed
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    mapcave[i][j] = 1;
                } else {
                    if (pseudorendomseed.nextInt(100) < fillPurcentage) {
                        mapcave[i][j] = 1;
                    } else {
                        mapcave[i][j] = 0;
                    }
                }
            }
        }
    }
    public int[][] creatMapfiltering () {
        int[][] filtred = new int[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++) {
                filtred[i][j] = fullAvgOneRange(i, j);
            }
        }
        return filtred ;
    }

    public void filtering(){
        applyfiltering(this.creatMapfiltering());
    }
    public void applyfiltering(int[][] mapfiltrering) { //appliquelefiltre
        for (int i = 1; i < width-1; i++) {
            for (int j = 1; j < height-1; j++) {
                if (mapfiltrering[i][j] > 4) {
                    mapcave[i][j] = 1;
                }
                if( mapfiltrering[i][j]  < 4) {
                    mapcave[i][j] = 0;
                }
            }
        }
    }
    public int fullAvgOneRange(int i , int j ) { // filtre de range 1 dans toutes les directions
        int sum = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if (k != 0 || l != 0) {
                    if( i+k >=0  && i+k < width && j+l>=0 && j+l<height ){
                        sum = sum + mapcave[i + k][j + l];   // a enlever plus tard fleme de gere les effet de bord
                    }
                }
            }
        }
        return sum;

    }
    public void placeWall( ){
        for ( int i = 1 ; i<width-1 ;i++){
            for ( int j =1 ;  j < height-1 ; j++){
                if (mapcave[i][j]==1 && mapcave[i][j+1]==0) {
                    mapcave[i][j] = 2 ;
                }
            }
        }
    }
    public int crusAvgOneRange(int i , int j) { // filtre en crois de range 1
        int sum = 0;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if ((k != 0 || l != 0) && (Math.abs(k) != Math.abs(l))) {
                    if( i+k > 0 && i+k < width && j+l>0 && j+l<height ){
                        sum = sum + 2*mapcave[i + k][j + l];   // a enlever plus tard fleme de gere les effet de bord
                    }
                }
            }
        }
        return sum ;
    }

    public void coloring(){
        for (int i= 0 ; i < width ; i++) {
            for(int j =0 ; j< height ; j++ ) {
                if(mapcave[i][j] == 0 ){
                    if (detection( i,j,2,0,0,30)) {
                        remplacement(i,j,0,1);
                    }
                }
            }
        }

    }


    public boolean detection(int i , int j , int setvalue , int access , int compteur , int limite ) {
        mapcave[i][j] = setvalue ;
        compteur++;
        if (compteur < limite) {
            for (int l = -1; l < 2; l++) {
                for (int k = -1; k < 2; k++) {
                    if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
                        if (mapcave[i + l][j + k] == access) {
                            if (!(detection(i + l, j + k, setvalue, access, compteur, limite))){
                                return false ;
                            }
                        }
                    }
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    public void remplacement (int i , int j , int origin , int end ) {
        mapcave[i][j] = end;
        for (int l = -1; l < 2; l++) {
            for (int k = -1; k < 2; k++) {
                if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
                    if (mapcave[i + l][j + k] == origin) {
                        remplacement(i+l,j+k,origin,end);
                    }
                }
            }
        }
    }

    public void randomFill(int fillPurcentage) {
        randomFill(System.currentTimeMillis(),fillPurcentage);
    }


    public int[][] getMapcave() {
        return mapcave;
    }

    public void setMapcave(int[][] mapcave) {
        this.mapcave = mapcave;
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
