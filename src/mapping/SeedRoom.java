package mapping;

import tools.Coordinate;

import java.util.Random;

class SeedRoom extends Room {
    private final Coordinate [] listSeed;
    private final int [] listSeedInterger ;
    private final int nbSeed;
    public SeedRoom (int width , int height , Random pseudoRandomList, int[] listSeedInterger){
        super(width , height , pseudoRandomList );

        this.listSeedInterger=listSeedInterger;

        nbSeed=listSeedInterger.length;
        listSeed = new Coordinate[nbSeed];
        placeSeed();
        fillMap();
        for( int i = 0 ; i<50 ;i++){
            applyfiltering(fullnRangefiltering(3),31);
        }

    }

    private void placeSeed() {
        for(int i = 0 ; i < nbSeed ; i++){
            int x= pseudoRandomList.nextInt(width);
            int y= pseudoRandomList.nextInt(height);
            listSeed[i] = new Coordinate(x,y);
        }
    }

    private void fillMap(){
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


}
