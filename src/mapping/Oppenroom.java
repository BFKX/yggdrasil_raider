package mapping;

import java.util.Random;

public class Oppenroom extends Room {
    int [][] maproom ;
    Random pseudoRandommlist;
    public Oppenroom(Random pseudoRandommlist) {
        maproom = new int[this.getWidth()][this.getHeight()];

    }
    public void addGround() {
        for (int column = 0; column < this.getWidth(); column++ ) {
            maproom[column][this.getWidth()] = 1 ;
        }
        int[][] temp = addRandomGround();
        for(int i=0 ; i<6 ; i++ ){
            temp= horizontalFiltering(temp);
        }

    }
    public int [][] addRandomGround(){
        int[] [] grounds = new int[this.getWidth()/10][this.getHeight()];
        for(  int i = 0, j = 0; i <grounds.length && j<this.getHeight(); i++,j++ ) {
            grounds[i][j] = pseudoRandommlist.nextInt(this.getHeight()/10) ;
        }
        return grounds;
    }
    public int [][] horizontalFiltering(int [] [] tab ){
        int[] [] filtred = new int[tab.length][tab[0].length];
        for(  int i = 0, j = 1; i <tab.length && j<this.getHeight()-1; i++,j++ ) {
            filtred[i][j] = tab[i][j-1] * tab[i][j+1] + (tab[i][j] * (1-tab[i][j-1]) + (1 - tab[i][j+1]) )*(tab[i][j-1]+tab[i][j+1]);
        }
        return filtred;
    }
}
