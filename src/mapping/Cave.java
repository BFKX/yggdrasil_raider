package mapping;
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
                if( endseed.nextInt() <fillPurcentage ) {
                    mapcave[i][j] = 1;
                }
                else  {
                    mapcave[i][j] = 0;
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
