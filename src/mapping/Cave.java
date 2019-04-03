package mapping;

import tools.Coordinate;
import java.util.ArrayList;
import java.util.Random;

public class Cave extends Room {
    private int [] [] mapcave  ;
    private int width ;
    private int height ;

    public Cave (int width , int height,Random pseudoRandomList,int fillPurcentage) {
        super (width,height,pseudoRandomList);
        this.width=width;
        this.height=height;
        this.mapcave = new int[width][height] ;
        randomFill(fillPurcentage);
        for (int i = 0; i < 25; i++){
            filtering();
        }
        for (int i = 0; i < 50; i++){
            additiveFiltering();
        }
        placeWall();
    }

    /**
     * generation d'un bruit
     * @param fillPurcentage
     */
    public void randomFill(int fillPurcentage) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    mapcave[i][j] = 1;
                } else {
                    if (pseudoRandomList.nextInt(100) < fillPurcentage) {
                        mapcave[i][j] = 1;
                    } else {
                        mapcave[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * cree un tableau filtrer a partir de la map
     * @return
     */
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

    /**
     * applique le fitre a mapcav
     * @param mapfiltrering
     */
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

    /**
     * filtre de range 1 aux tours de chaque points
     * @param i
     * @param j
     * @return
     */

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

    /**
     * place des murs
     */
    public void placeWall( ){
        for ( int i = 0 ; i<width ;i++){
            for ( int j =0 ;  j < height ; j++){
                if (mapcave[i][j]!=0 ) {
                    if (j-1>0 && mapcave[i][j-1] == 0){ //north
                        mapcave[i][j] = mapcave[i][j]+1 ;
                    }
                    if (i-1>0 && mapcave[i-1][j] == 0){ //west
                        mapcave[i][j] = mapcave[i][j] +2 ;
                    }
                    if ( j+1 < this.height && mapcave[i][j+1] == 0){//south
                        mapcave[i][j] = mapcave[i][j]+5 ;
                    }
                    if ( i+1 <this.width && mapcave[i+1][j] == 0){//east
                        mapcave[i][j] = mapcave[i][j]+11 ;
                    }

                }
            }
        }
    }

    public  void additiveFiltering(){
        int [][] temp = new int [width] [height] ;
        for ( int i = 0 ; i<width ;i++) {
            for (int j = 0; j < height ; j++) {
                if(mapcave[i][j] == 0) {
                    int sum = 0;
                    for (int l = -2; l < 3; l++) {
                        for (int k = -2; k < 3; k++) {
                            if (i + l < width && j + k >= 0 && j + k < height && i + l>= 0 ) {
                                sum=sum+mapcave[i+l][j+k];
                            }
                        }
                    }
                    if(sum>13) {
                        temp[i][j] = 1 ;
                    }else {
                        temp[i][j]=mapcave[i][j];
                    }
                }else {
                    temp[i][j]=mapcave[i][j];
                }
            }
            }
        for ( int i = 0 ; i<width;i++) {
            for (int j = 0; j < height ; j++) {
                mapcave[i][j]=temp[i][j];
            }
        }
    }

    public void coloring(){
        int setvalue = -1 ;
        for (int i= 0 ; i < width ; i++) {
            for(int j =0 ; j< height ; j++ ) {
                if(mapcave[i][j] == 0 ){
                    if (detection( i,j,setvalue,0,0,5)) {
                        remplacement(i,j,0,25);
                    }
                    setvalue -- ;
                }
            }
        }
        for (int i= 0 ; i < width ; i++) {
            for (int j = 0; j < height; j++) {
                if(mapcave[i][j] < 0  ){
                    mapcave[i][j] = 0 ;
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
                        if (mapcave[i + l][j + k]  <= access) {
                            if (!(detection(i + l, j + k, setvalue, access, compteur, limite))){
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

    /* creation de lien entre les caviter
     */
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
