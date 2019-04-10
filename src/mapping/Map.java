package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import tools.Coordinate;

import java.awt.Toolkit;
import java.util.Random;

public class Map {
    private int[][] map;
    private int lines;
    private int columns;
    private Cave origine;
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
    final private Image groundVar = new Image("resources/images/groundVar.png");
    final private Image character = new Image("resources/images/waitingCharacter.png");
    final double SIDE = HEIGHT / 60;
    private Random pseudoRandomList;
    private double sideMiniMap = SIDE / 12;
    private double origineXMiniMap;
    private double origineYMiniMap;

    public Map(int columns, int lines) {
        this.lines = lines;
        this.columns = columns;
        this.map = new int[columns][lines];
        pseudoRandomList =  new Random(System.currentTimeMillis());
        //origine = new SeedRoom(columns ,lines,   pseudoRandomList ,new int[] {1,1,1,0,0} );
        origine = new Cave(columns , lines , pseudoRandomList);
        //origine.placeRoom(pseudoRandomList);
        curent = origine ;
        this.map= origine.getMap();
        addGroundVariation2(new int []{25,25,25,25},50000,150);
        origineXMiniMap = WIDTH - columns * sideMiniMap;
        origineYMiniMap = HEIGHT - lines * sideMiniMap;

    }
    public void update (){
        this.map = curent.getMap();
    }
    public void createSeedRoom(){
        Random pseudoRandomList =  new Random(System.currentTimeMillis());
        curent = new SeedRoom( columns ,lines , pseudoRandomList ,new int[] {1,1,pseudoRandomList.nextInt(2),
                pseudoRandomList.nextInt(2)} );
        update();
    }
    public void createCave(){
        Random pseudoRandomList =  new Random(System.currentTimeMillis());
        origine = new Cave(columns , lines , pseudoRandomList);
        curent = origine ;
        this.map= origine.getMap();
    }

  public void addGroundVariation1(int fillPurcentage){
    Random pseudorendom =  new Random(System.currentTimeMillis());
    Cave temp = new Cave(columns, lines,pseudorendom);
    for (int i = 0; i < 50; i++) {
        temp.applyfiltering(temp.fullnRangefiltering(1),4);
    }
    for (int column = 0; column < columns; column++ ) {
        for (int line = 0; line < lines; line++) {
            if (map[column][line]==0){
                if( temp.getMap()[column][line] == 1) {
                    map[column][line] = 25;
                }
            }
        }
    }
    }
    public void addGroundVariation2(int [] seeds, int rayon , int limite){
        int [][] temp = new int [columns][lines];
        Coordinate [] seedsCordinates = new Coordinate[seeds.length];
        for ( int i= 0 ; i<seeds.length ; i++){
            int x = pseudoRandomList.nextInt(columns);
            int y = pseudoRandomList.nextInt(lines);
            seedsCordinates[i] = new Coordinate(x,y);
            temp[x][y]=seeds[i];
        }
        for ( int i =0 ; i< columns ;i++){
            for(int j = 0 ; j< lines ;j++){
               if (map[i][j] == 0) {
                   Coordinate ij = new Coordinate(i, j);
                   int k = 0;
                   for (Coordinate c : seedsCordinates) {
                       double d = c.distance(ij);
                       if (d < rayon) {
                           if (Math.abs(pseudoRandomList.nextGaussian())  * d < limite) {
                               map[i][j] = seeds[k];
                           }
                       }
                       k++;
                   }
               }
            }
        }
    }

    public void display(GraphicsContext gc, Coordinate positionCharac) {
        int initColumn = (int)(positionCharac.getX() / SIDE) - 90;
        int initLine = (int)(positionCharac.getY() / SIDE) - 60;
        int lineOffset = (int)(positionCharac.getY() / SIDE) - 11;
        int columnOffset = (int)(positionCharac.getX() / SIDE) - 19;
        for (int column = initColumn; column < initColumn + 180; column++) {
            if(column < 0 || column >= columns) { continue; }
            for (int line = initLine; line < initLine + 120; line++) {
                if(line < 0 || line >= lines) { continue; }
                gc.drawImage(spriteSelector(map[column][line]), (column - columnOffset) * SIDE - positionCharac.getX() % SIDE, (line - lineOffset) * SIDE - positionCharac.getY() % SIDE, SIDE, SIDE);
            }
        }
    }

    public void displayMiniMap(GraphicsContext gc, Coordinate positionCharac) {
        Paint fill = gc.getFill();
        gc.setFill(Color.RED);
        double sideMiniMap10 = 10 * sideMiniMap;
        for (int column = 0; column < columns; column++ ) {
            for (int line = 0; line < lines; line++) {
                gc.drawImage(spriteSelector(map[column][line]), origineXMiniMap + column * sideMiniMap, origineYMiniMap + line * sideMiniMap, sideMiniMap, sideMiniMap);
            }
        }
        gc.drawImage(character, origineXMiniMap + positionCharac.getX() / sideMiniMap, origineYMiniMap + positionCharac.getY() / sideMiniMap, sideMiniMap10, sideMiniMap10);
        gc.setFill(fill);
    }

    private Image spriteSelector(int square) {
        switch(square) {
            case 0:
                return ground;
            case 1:
                return voidImage;
            case 2:
                return nWall;
            case 3:
                return wWall;
            case 4:
                return nwWall;
            case 6:
                return sWall;
            case 8:
                return swWall;
            case 9:
                return wCloseWall;
            case 12:
                return eWall;
            case 13:
                return neWall;
            case 15:
                return nCloseWall;
            case 17:
                return seWall;
            case 18:
                return eCloseWall;
            case 19:
                return sCloseWall;
            case 21:
                return nwCorner;
            case 25:
                return groundVar;
            case 41:
                return swCorner;
            case 82:
                return seCorner;
            case 163:
                return neCorner;
            default:
                return red;
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