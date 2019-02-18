package mapping;

import javafx.scene.image.Image;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.*;

public class Mapping {

    private int[][] map;
    private Case[][] casemap;
    private int nbline;
    private int nbcolumn;
    double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

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
        this.casemap[i][j].setHitbox(new Hitbox(new Coordinate(i*255,j*255),255 , 255 ));
    }

    public void addGround() {
        for ( int j = 0 ;  j<nbcolumn ; j++ ) {
            addCase(0,j, 1, new Image("images/ground.png"));
        }
    }


}