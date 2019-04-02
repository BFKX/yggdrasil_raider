package mapping;
import main.Main;
import tools.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Cave extends Room {
    private int [] [] mapcave  ;
    private int width ;
    private int height ;
    private ArrayList<ArrayList<Coordinate>> roomsborders =new ArrayList<ArrayList<Coordinate>>();
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
    public void placeWall( ){
        for ( int i = 0 ; i<width ;i++){
            for ( int j =0 ;  j < height ; j++){
                if (mapcave[i][j]!=0 ) {
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

    public void test(){
        int nbPasage = -1 ;
        for ( int j =1 ;  j < height-1 ; j++) {
            for ( int i = 1 ; i<width-1 ;i++){
                if(mapcave[i][j]==0 && mapcave[i+1][j]==1 || mapcave[i][j]==1 && mapcave[i+1][j]==0){
                    nbPasage--;
                }else if( nbPasage % 2 == -1 ){
                    mapcave[i][j]=nbPasage;
                }
            }
        }
        int [] sum= new int [-(nbPasage)+1] ;
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if(mapcave[i][j] < 0) {
                    sum[-mapcave[i][j]]++;
                }
            }
        }
        for ( int i : sum){
            System.out.println(i);
        }
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if(mapcave[i][j]<0 && sum[-mapcave[i][j]] < 50){
                    mapcave[i][j]=25;
                }else {
                    if (-sum[mapcave[i][j]] != 0) {
                        System.out.println(-sum[mapcave[i][j]]);
                    }
                }
            }
        }
    }
    public int test2( int i , int j, int val , int compteur  ){
        mapcave[i][j] = val ;
        compteur ++;
        for (int l = -1; l < 2; l++) {
            for (int k = -1; k < 2; k++) {
                if (i + l >= 0 && i + l < width && j + k >= 0 && j + k < height) {
                    if (mapcave[i+l][j+k]!=1 && mapcave[i+l][j+k]!=val){
                        try {
                            compteur = compteur + test2(i + l, j + k, val, compteur);
                        }
                        catch (StackOverflowError e ){
                            break;
                        }
                    }
                }
            }
        }
        return compteur;
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

    public  void fillSmal(){
        int val = -1 ;
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if(mapcave[i][j]==0){
                    int compteur = test2(i,j,val,0);
                    if ( compteur < 30 ) {
                        remplacement(i, j, val, 25);
                    }
                    val--;
                }
             }
        }
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if (mapcave[i][j] < 0) {
                    mapcave[i][j]=0;
                }
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
    public void findBorder(){
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if(mapcave[i][j] == 0) {
                    if(mapcave[i-1][j]==1 || mapcave[i+1][j]==1||mapcave[i][j-1]==1|| mapcave[i+1][j+1]==1 ||mapcave[i-1][j-1]==1 || mapcave[i+1][j]==1|| mapcave[i-1][j+1]==1 || mapcave[i+1][j-1]==1  ) {
                        mapcave[i][j] = -1;
                    }
                }
            }
        }
    }

    public int findContour(){
        int k = -2 ;
        for ( int i = 1 ; i<width-1 ;i++) {
            for (int j = 1; j < height - 1; j++) {
                if (mapcave[i][j]==-1) {
                    ArrayList<Coordinate> roomBorder= new ArrayList<Coordinate>();
                    findminusone(i,j,k,roomBorder,0 );
                    for (Coordinate c : roomBorder){
                        System.out.println(c.toString());
                    }
                    roomsborders.add(roomBorder);
                }
            }
        }
        return k ;
    }

    public void findminusone(int i , int j, int val,ArrayList roomBorder,int compteur){ //trouve les -1 lier les rajoute a roomborder et les tranforme en 1
        compteur++;
        System.out.println(compteur);
        mapcave[i][j]=val;
        roomBorder.add(new Coordinate(i,j));
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                if(mapcave[i+k][j+l]==-1){
                    //System.out.println("in"+  " i , j : " + (i+k) +"," + (j+l) );
                    findminusone(i+k,j+l,val,roomBorder,compteur);
                }
            }
        }
        System.out.println("end");
    }

    private ArrayList<Coordinate> findConectionpoint(ArrayList<ArrayList<Coordinate>> roomsborders){ // retournes les coordonée des laison
        ArrayList<Coordinate> liasoncoord= new ArrayList<Coordinate>(); // liste des plus peuties coordonée
        Iterator<ArrayList<Coordinate>> it = roomsborders.iterator();
        for (ArrayList<Coordinate> temp1 : roomsborders){
            Coordinate c1max = null;
            Coordinate c2max = null;
            for (ArrayList<Coordinate> temp2 : roomsborders){
                if( ! temp1.equals(temp2)) {
                    double min = 999999999; //gere plus tard le premier min
                    for (Coordinate c1 : temp1) {
                        for (Coordinate c2 : temp2) {
                            if (c2.length(c1) < min) {
                                min = c2.length(c1);
                                c1max = c1;
                                c2max = c2;
                            }
                        }
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


    public  void creatLink(){
        findBorder();
        int k = findContour();
        ArrayList<Coordinate> conectionpoints = findConectionpoint(roomsborders);
        Iterator<Coordinate> it = conectionpoints.iterator();
        while ( it.hasNext() ) {
            Coordinate c1 = it.next();
            Coordinate c2 = it.next();
            //System.out.println(" c1: " + c1.toString() +"; c2 : " + c2.toString());
            //dig(c1,c2);
            mapcave[(int)c1.getX()][(int)c1.getY()] = -25;
            mapcave[(int)c2.getX()][(int)c2.getY()] = -25;
        }
    }

    public void dig(Coordinate c1 , Coordinate c2 ) {
        double vectX = ( c2.getX() - c1.getX()) ;
        double vectY = (c2.getY() - c1.getY()) ;
        int norme = (int) Math.sqrt(Math.pow(vectX,2) + Math.pow(vectY,2));
        int vectXi =  Math.round ((float)(vectX/(norme+1)));
        int vectYi =  Math.round ((float)(vectY /(norme+1)));
        Coordinate current =  c1 ;
        System.out.println(" c1: " + c1.toString() +"; c2 : " + c2.toString());
        System.out.println("VectX: " + vectX + ';' + "VectY:" + vectY );
        if(vectXi!=0 || vectYi != 0){
            while (mapcave[Math.round((float)(current.getX()))][Math.round((float)(current.getY()))]!=0&& Math.round((float)(current.getX()))>0&& Math.round((float)(current.getY()))>0 && Math.round((float)(current.getX()))<height && Math.round((float)(current.getY()))<width) {
                System.out.println("curx " + Math.round((float) current.getX()) + "cury" + Math.round((float) current.getY()));
                mapcave[Math.round((float) current.getY())][Math.round((float) current.getX())] = -25;
                current.sum(vectXi, vectYi);
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
