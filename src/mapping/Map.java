package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.Toolkit;
import java.util.Random;

public class Map {
    private int[][] map;
    private Case[][] mapCases;
    private int lines;
    private int columns;
    private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private Image voidImage = new Image("images/void.png");
    private Image sWall = new Image("resources/images/sWall.png");
    private Image torch = new Image("resources/images/torch_2.png");
    private Random pseudoRandomList ;
    public Map(int columns, int lines) {
        this.lines = lines;
        this.columns = columns;
        this.map = new int[columns][lines];
        this.mapCases = new Case[columns][lines];
        for (int i = 0; i < columns; i++ ) {
            for (int j = 0; j < lines; j++) {
                this.mapCases[i][j] = new Case(0, null, null);
            }
        }
    }

    private void addCase(int column, int line, int type, Image image) {
        this.map[column][line] = type ;
        this.mapCases[column][line].setType(type);
        this.mapCases[column][line].setImage(image);
        this.mapCases[column][line].setHitbox(new Hitbox(new Coordinate(WIDTH * column / columns,HEIGHT * line / lines),WIDTH / columns,HEIGHT / lines));
    }

    public void creatCave(int fillPurcentage) {
        Cave cave = new Cave(columns, lines,new Random(System.currentTimeMillis()));
        cave.randomFill(fillPurcentage);
        for (int i = 0; i < 15; i++){
            cave.filtering();
        }
        cave.placeWall();
        cave.placeTorch();
        map = cave.getMapcave() ;

    }
    public void creatOppenroom(){
        Oppenroom open = new Oppenroom(this.columns,this.lines,new Random(System.currentTimeMillis()));
        open.addGround();
        this.map=open.maproom;
    }
    public void display(GraphicsContext gc) {
        Image sprite;
        final double height = HEIGHT / lines;
        final double width = WIDTH / columns;
        for (int column = 0; column < columns; column++ ) {
            for (int line = 0; line < lines; line++) {
                switch(map[column][line]) {
                    case 1: sprite = voidImage; break;
                    case 2: sprite = sWall; break;
                    case 3: sprite = sWall; break;
                    default: sprite = voidImage;
                }
                gc.drawImage(sprite, column * width, line * height,width,height)
            }
        }
    }
}