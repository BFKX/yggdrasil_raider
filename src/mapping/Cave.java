package mapping;
import tools.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Cave extends Room {
    private int [] [] mapcave  ;
    private int width ;
    private int height ;
    private ArrayList<ArrayList<Coordinate>> roomsborders ;
    public Cave (int width , int height,Random pseudoRandomList) {
        super (new Coordinate(0,0),width,height,pseudoRandomList);
        this.width=width;
        this.height=height;
        this.mapcave = new int[width][height] ;

    }
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

    public void findBorder(){
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if ((k != 0 || l != 0 && mapcave[i+k][j+l]==0)){
                            mapcave[i][j]=-1;
                        }
                    }
                }
            }
        }
    }

    public int  findContour(){
        int k = -1 ;
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if (mapcave[i][j]==-1) {
                    ArrayList<Coordinate> roomBorder= new ArrayList<Coordinate>();
                    k--;
                    findminusone(i,j,k,roomBorder);
                    roomsborders.add(roomBorder);
                }
            }
        }
        return k ;
    }

    public void findminusone(int i , int j, int val,ArrayList roomBorder){ //trouve les -1 lier les rajoute a roomborder et les tranforme en 1
        mapcave[i][j]=val;
        roomBorder.add(new Coordinate(i,j));
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if(mapcave[i+k][j+l]==-1){
                    findminusone(i+k,j+l,val,roomBorder);
                }
            }
        }
    }

    private ArrayList<Coordinate> findConectionpoint(ArrayList<ArrayList<Coordinate>> roomsborders){ // retournes les coordonée des laison

        ArrayList<Coordinate> liasoncoord= new ArrayList<Coordinate>(); // liste des plus peuties coordonée
        for (ArrayList<Coordinate> temp : roomsborders){
            double min=999999999 ; //gere plus tard le premier min
            Coordinate c1max= null;
            Coordinate c2max= null;
            for(Coordinate c1 : temp){
                for(Coordinate c2 : temp){
                    if(c2.length(c1)<min){
                        min=c2.length(c1);
                        c1max=c1;
                        c2max=c2;
                    }
                }
            }
            liasoncoord.add(c1max);
            liasoncoord.add(c2max);
        }
        return liasoncoord;
    }

    public void displayconecteur(ArrayList<Coordinate> liaisoncoord){
        for(Coordinate c: liaisoncoord ){
            System.out.println(c.toString());
        }
    }

    public void placeWall( ){
        for ( int i = 0 ; i<width ;i++){
            for ( int j =0 ;  j < height ; j++){
                if (mapcave[i][j]==1 ) {
                    if (j-1>0 && mapcave[i][j-1]==0  ){ //north
                        mapcave[i][j] = mapcave[i][j]+1 ;
                    }
                    if (i-1>0 && mapcave[i-1][j]==0 ){ //west
                        mapcave[i][j] = mapcave[i][j] +2 ;
                    }
                    if ( j+1 < this.height && mapcave[i][j+1]==0){//south
                        mapcave[i][j] = mapcave[i][j]+5 ;
                    }
                    if ( i+1 <this.width && mapcave[i+1][j]==0){//east
                        mapcave[i][j] = mapcave[i][j]+11 ;
                    }

                }
            }
        }
    }

    public void placeTorch( ){
        for ( int i = 1 ; i<width-1 ;i++){
            for ( int j =1 ;  j < height-1 ; j++){
                if (mapcave[i][j]==2 ) {
                    if(pseudoRandomList.nextInt(20)==1){
                        mapcave[i][j] = 3 ;

                    }
                }
            }
        }
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
