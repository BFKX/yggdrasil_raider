package mapping;

import tools.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SeedRoom extends Room {
    Coordinate [] listSeed;
    int [] listSeedInterger ;
    int nbSeed;
    public SeedRoom (int width , int height , Random pseudoRandomList, int[] listSeedInterger){
        super(width , height , pseudoRandomList );
        System.out.println("coucou");
        this.listSeedInterger=listSeedInterger;
        System.out.println(listSeedInterger[0]);
        nbSeed=listSeedInterger.length;
        listSeed = new Coordinate[nbSeed];
        placeSeed();
        fillMap();
        for( int i = 0 ; i<50 ;i++){
            applyfiltering(fullnRangefiltering(3),31);
        }

    }

    public void placeSeed() {
        for(int i = 0 ; i < nbSeed ; i++){
            int x= pseudoRandomList.nextInt(width);
            int y= pseudoRandomList.nextInt(height);
            listSeed[i] = new Coordinate(x,y);
        }
    }

    public void fillMap(){
        for (int i = 0 ; i < width ;i++){
            for(int j = 0 ;  j < height ; j++){
                double [] distances = new double[nbSeed];
                double sum = 0;
                Coordinate curent = new Coordinate(i,j);
                for ( int k= 0 ; k<nbSeed ; k++){
                    distances[k] = Math.exp(1/(listSeed[k].distance(curent)+1));
                    sum+=distances[k];
                }
                int randominteger = pseudoRandomList.nextInt((int)sum);
                sum = 0 ;
                for ( int k= 0 ; k<nbSeed ; k++){
                   sum= sum + distances[k];
                   if ( randominteger < sum){

                       map[i][j] = listSeedInterger[k];

                       break;
                   }
                }
            }
        }
    }
    public void applyfiltering(int[][] mapfiltrering, int limit) { //appliquelefiltre
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (mapfiltrering[i][j] >=limit) {
                    map[i][j] = 1;
                }
                if (mapfiltrering[i][j] < limit) {
                    map[i][j] = 0;
                }
            }
        }
    }


    public int[][] fullnRangefiltering(int n) {
        int[][] temp = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int sum = 0;
                for (int l = -n; l < n + 1; l++) {
                    for (int k = -n; k < n + 1; k++) {
                        if (i + l < width && j + k >= 0 && j + k < height && i + l >= 0) {
                            sum = sum + map[i + l][j + k];
                        } else {
                            sum = 25;
                            break;
                        }
                    }
                }
                temp[i][j] = sum;
            }
        }
        return temp;
    }


}
