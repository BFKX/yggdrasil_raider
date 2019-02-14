package mapping;

import javafx.scene.image.Image;

public class Mapping {

    private int[][] map;
    private Case[][] casemap;
    private int nbline;
    private int nbcolumn;

    public Mapping(int column, int line ) {

        this.nbline = line;
        this.nbcolumn = column;
        this.map = new int[line][column];
        this.casemap = new Case[column][line];

        for (int i = 0; i < line; i++ ) {

            for (int j = 0; j < column; j++) {

                this.casemap[i][j] = new Case(0, null, null);
            }
        }
    }

    public void addCase(int i , int j , int type , Image image) {

        this.map[i][j] = type ;
        this.casemap[i][j].setType(type);
        this.casemap[i][j].setImage(image);
    }

    public void addGround() {

        for ( int j = 0 ;  j<nbcolumn ; j++ ) {

            addCase(j,0, 1, new Image("resources/graphics/ground.png"));
        }
    }

}
