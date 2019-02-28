package mapping;
import java.util.ArrayList;
import java.util.Random;

public class Cave {
    private int [] [] mapcave  ;
    private int width ;
    private int height ;

    public Cave (int width , int height) {
        this.width=width;
        this.height=height;
        this.mapcave = new int[width][height] ;
    }

    public void randomFill(long seed , int fillPurcentage){
        Random endseed = new Random(seed ) ;  //  crée une distribution aléatroi qui depend de la seed
        for (int i = 0 ; i < width ; i++) {
            for ( int j = 0 ; j<height ; j++){
                if( i == 0 || i == width-1 || j== 0 || j== height-1 ) {
                    mapcave[i][j] = 1 ;
                }
                else {
                    if (endseed.nextInt() < fillPurcentage) {
                        mapcave[i][j] = 1;
                    } else {
                        mapcave[i][j] = 0;
                    }
                }
            }
        }
    }
    public void filtering () {
        int[][] sumtab = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int sum = 0;
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (k != 0 || l != 0) {
                            try {
                                sum = sum + mapcave[i + k][j + l];   // a enlever plus tard fleme de gere les effet de bord
                            } catch (IndexOutOfBoundsException e) { sum++;
                            }
                        }
                    }
                }
                sumtab[i][j]=sum;
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (sumtab[i][j] > 4) {
                    mapcave[i][j] = 1;
                }
                if( sumtab[i][j]  < 4) {
                    mapcave[i][j] = 0;
                }
            }
        }
    }
    public void coloring(){
        int k=5 ;
        ArrayList <Integer> regionsise = new ArrayList<Integer>() ;
        for (int i= 0 ; i < width ; i++) {
            for(int j =0 ; j< height ; j++ ) {
                if(mapcave[i][j] == 0 ){
                    if (coloringRegion(i,j,k,0) <50) {
                        coloringRegion(i,j,1,k);
                    }
                    else {
                        regionsise.add(k);
                    }
                }
            }
        }

    }


    public int coloringRegion(int i , int j , int setvalue , int access ) {
        mapcave[i][j] = setvalue;
        int sum = 0 ;
        for (int l = -1; l < 2; l++) {
            for(int k =-1 ; k<2 ; k++) {
                if ( i+ l >= 0 && i+l < width && j+k >=0 && i+l < height ){
                    if (mapcave[i + l][j + k] == access) {
                        sum= sum + coloringRegion(i + l, j + k, setvalue, access);
                    }
                }
            }
        }
        return sum;
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
