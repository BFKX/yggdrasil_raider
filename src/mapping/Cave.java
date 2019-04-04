package mapping;

import java.util.Random;

public class Cave extends Room {

    public Cave (int width , int height,Random pseudoRandomList,int fillPurcentage) {
        super (width,height,pseudoRandomList);
        this.width=width;
        this.height=height;
        this.map = new int[width][height] ;
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
                    map[i][j] = 1;
                } else {
                    if (pseudoRandomList.nextInt(100) < fillPurcentage) {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 0;
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
                    map[i][j] = 1;
                }
                if( mapfiltrering[i][j]  < 4) {
                    map[i][j] = 0;
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
                        sum = sum + map[i + k][j + l];   // a enlever plus tard fleme de gere les effet de bord
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
                if (map[i][j]!=0 ) {
                    if (j-1>0 && map[i][j-1] == 0){ //north
                        map[i][j] = map[i][j]+1 ;
                    }
                    if (i-1>0 && map[i-1][j] == 0){ //west
                        map[i][j] = map[i][j] +2 ;
                    }
                    if ( j+1 < this.height && map[i][j+1] == 0){//south
                        map[i][j] = map[i][j]+5 ;
                    }
                    if ( i+1 <this.width && map[i+1][j] == 0){//east
                        map[i][j] = map[i][j]+11 ;
                    }
                    if(map[i][j]==1){
                        if (j-1>0 && i-1>0 && map[i-1][j-1] == 0){ //north est
                            map[i][j] = map[i][j]+20 ;
                        }
                        if (i-1>0 && j+1<this.height &&map[i-1][j+1] == 0){ //sud est
                            map[i][j] = map[i][j] +40 ;
                        }
                        if ( j+1 < this.height &&  i+1 < this.width && map[i+1][j+1] == 0){//sud west
                            map[i][j] = map[i][j]+81 ;
                        }
                        if ( i+1 <this.width && j-1 >0 && map[i+1][j-1] == 0){//nord west
                            map[i][j] = map[i][j]+162 ;
                        }
                    }
                }
            }
        }
    }

    public  void additiveFiltering(){
        int [][] temp = new int [width] [height] ;
        for ( int i = 0 ; i<width ;i++) {
            for (int j = 0; j < height ; j++) {
                if(map[i][j] == 0) {
                    int sum = 0;
                    for (int l = -2; l < 3; l++) {
                        for (int k = -2; k < 3; k++) {
                            if (i + l < width && j + k >= 0 && j + k < height && i + l>= 0 ) {
                                sum=sum+ map[i+l][j+k];
                            }
                        }
                    }
                    if(sum>13) {
                        temp[i][j] = 1 ;
                    }else {
                        temp[i][j]= map[i][j];
                    }
                }else {
                    temp[i][j]= map[i][j];
                }
            }
            }
        for ( int i = 0 ; i<width;i++) {
            for (int j = 0; j < height ; j++) {
                map[i][j]=temp[i][j];
            }
        }
    }

    public void coloring(){
        int setvalue = -1 ;
        for (int i= 0 ; i < width ; i++) {
            for(int j =0 ; j< height ; j++ ) {
                if(map[i][j] == 0 ){
                    if (detection( i,j,setvalue,0,0,5)) {
                        remplacement(i,j,0,25);
                    }
                    setvalue -- ;
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

    public boolean detection(int i , int j , int setvalue , int access , int compteur , int limite ) {
        map[i][j] = setvalue ;
        compteur++;
        if (compteur < limite) {
            for (int l = -1; l < 2; l++) {
                for (int k = -1; k < 2; k++) {
                    if (i + l >= 0 && i + l < width && j + k >= 0 && i + l < height) {
                        if (map[i + l][j + k]  <= access) {
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
