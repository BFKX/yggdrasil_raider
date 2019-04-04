package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;

import java.awt.Toolkit;
import java.util.Random;

public class Map {
    private int[][] map;
    private int lines;
    private int columns;
    private Room origine;
    private Room curent;
    final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    final private Image voidImage = new Image("resources/images/void.png");
    final private Image sWall = new Image("resources/images/sWall.png");
    final private Image eWall = new Image("resources/images/eWall.png");
    final private Image nWall = new Image("resources/images/nWall.png");
    final private Image wWall = new Image("resources/images/wWall.png");
    final private Image seWall = new Image("resources/images/seWall.png");
    final private Image neWall = new Image("resources/images/neWall.png");
    final private Image swWall = new Image("resources/images/swWall.png");
    final private Image nwWall = new Image("resources/images/nwWall.png");
    final private Image ground = new Image("resources/images/ground.png");
    final private Image seCorner = new Image("resources/images/seCorner.png");
    final private Image swCorner = new Image("resources/images/swCorner.png");
    final private Image neCorner = new Image("resources/images/neCorner.png");
    final private Image nwCorner = new Image("resources/images/nwCorner.png");
    final private Image sCloseWall = new Image("resources/images/sCloseWall.png");
    final private Image eCloseWall = new Image("resources/images/eCloseWall.png");
    final private Image wCloseWall = new Image("resources/images/wCloseWall.png");
    final private Image nCloseWall = new Image("resources/images/nCloseWall.png");
    final private Image red = new Image("resources/images/red.png");
    private Random pseudoRandomList ;

    public Map(int columns, int lines) {
        this.lines = lines;
        this.columns = columns;
        this.map = new int[columns][lines];
        pseudoRandomList =  new Random(System.currentTimeMillis());
        origine = new Cave(columns , lines , pseudoRandomList);
        for (int i =0 ; i<10 ; i++ ){
            origine.placeRoom(pseudoRandomList, new Cave(columns,lines,pseudoRandomList));
        }
        curent = origine ;
        this.map= origine.getMap();
    }
    public void update (){
        this.map = curent.getMap();
    }

    public void createCave(){
        Random pseudoRandomList =  new Random(System.currentTimeMillis());
        origine = new Cave(columns , lines , pseudoRandomList);
        curent = origine ;
        this.map= origine.getMap();
    }

    /*
    public void addGroundVariation(int fillPurcentage){
        Random pseudorendom =  new Random(System.currentTimeMillis());
        //Cave temp = new Cave(columns, lines,pseudorendom);
        temp.randomFill(fillPurcentage);
        for (int i = 0; i < 50; i++){
            temp.filtering();
        }
        for (int column = 0; column < columns; column++ ) {
            for (int line = 0; line < lines; line++) {
                if (map[column][line]==0){
                    if( temp.getMapcave()[column][line] == 1) {
                        map[column][line] = 25;
                    }
                }
            }
        }

    }
*/
    public void display(GraphicsContext gc, Coordinate positionCharac) {
        Image sprite;
        final double side = HEIGHT / 60;
        for (int column = (int)(positionCharac.getX() / side) - 90; column < (int)(positionCharac.getX() / side) + 90; column++) {
            if(column < 0 || column >= columns) { continue; }
            for (int line = (int)(positionCharac.getY() / side) - 60; line < (int)(positionCharac.getY() / side) + 60; line++) {
                if(line < 0 || line >= lines) { continue; }
                switch(map[column][line]) {
                    case 25: sprite = red; break;
                    case 0: sprite = ground; break;
                    case 1: sprite = voidImage; break;
                    case 2: sprite = nWall; break;
                    case 3: sprite = wWall; break;
                    case 4: sprite = nwWall; break;
                    case 6: sprite = sWall; break;
                    case 8: sprite = swWall; break;
                    case 9: sprite = wCloseWall; break;
                    case 12: sprite = eWall; break;
                    case 13: sprite = neWall; break;
                    case 15: sprite = nCloseWall; break;
                    case 17: sprite = seWall; break;
                    case 18: sprite = eCloseWall; break;
                    case 19: sprite = sCloseWall; break;
                    case 21: sprite = nwCorner; break;
                    case 41: sprite = swCorner; break;
                    case 82: sprite = seCorner; break;
                    case 163: sprite = neCorner; break;
                    default: sprite = red;
                }
                gc.drawImage(sprite, (column - ((int)(positionCharac.getX() / side) - 19)) * side - positionCharac.getX() % side, (line - ((int)(positionCharac.getY() / side) - 11)) * side - positionCharac.getY() % side, side, side);
            }
        }
    }

    public void moveNorth(){
        if(curent.getNorth() != null ){
            curent=curent.getNorth();
            update();
        }else{
            System.out.println("noNorth");
        }
    }
    public void moveSouth(){
        if(curent.getSouth()!= null){
            curent=curent.getSouth();
            update();
        }
        else {
            System.out.println("noSouth");
        }
    }
    public void moveEast(){
        if( curent.getEast() != null){
            curent=curent.getEast();
            update();
        }
        else {
            System.out.println("noEast");
        }
    }
    public void moveWest(){
        if(curent.getWest() != null){
            curent=curent.getWest();
            update();
        }
        else {
            System.out.println("noWest");
        }
    }

    public Room getOrigine(){
        return  origine;
    }
}