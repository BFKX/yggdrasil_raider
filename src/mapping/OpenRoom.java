package mapping;

import tools.Coordinate;

import java.util.Random;

public class OpenRoom extends Room {
    int [][] maproom ;
    public OpenRoom(int whidth , int height, Random pseudoRandomList) {
        super (whidth,height, pseudoRandomList);
        maproom = new int[this.getWidth()][this.getHeight()];
    }
    public void addGround() {
        for (int column = 0; column < this.getWidth(); column++ ) {
            maproom[column][this.getWidth()] = 1 ;
        }
    }

    public void addRandomGround(){
        int[] [] grounds = new int [this.getWidth()][this.getHeight()/10];
        for(  int i = 0 ; i< grounds.length ;  i++ ){
            for ( int j = 0; j <grounds[0].length ; j++ ) {
                System.out.println("{" + i +";" + j + "}");
                grounds[i][j] = pseudoRandomList.nextInt(this.getHeight()/10) ;
            }
        }
        System.out.println("end1");
        for( int i  = 0 ;  i<this.getWidth() ; i++ ){
            for (int j = this.getHeight(); j < this.getHeight() / 10; j--) {
                this.maproom[i][j] = grounds[i][j];
            }
        }
        System.out.println("end 2");
    }

    public int [][] horizontalFiltering(int [] [] tab ){
        int[] [] filtred = new int[tab.length][tab[0].length];
        for(  int i = 0 ;  i <tab.length ; i++ ) {
            for ( int j = 1; j<this.getHeight()-1 ; j++ ) {
                filtred[j][i] = tab[j-1][i] * tab[j+1][i] + (tab[j][i] * (1-tab[j-1][i]) + (1 - tab[j+1][i]) )*(tab[j-1][i]+tab[j+1][i]);
            }
        }
        System.out.println("end2");
        return filtred;
    }
}
