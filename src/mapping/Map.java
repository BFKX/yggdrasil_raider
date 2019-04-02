package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.Toolkit;
import java.util.Random;

public class Map {
    private int[][] map;
    private int lines;
    private int columns;
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
    final private Image red = new Image("resources/images/red.png");
    private Random pseudoRandomList ;

    public Map(int columns, int lines) {
        this.lines = lines;
        this.columns = columns;
        this.map = new int[columns][lines];
    }



    public void createCave(int fillPurcentage, boolean link) {
        Cave cave = new Cave(columns, lines,new Random(System.currentTimeMillis()));
        cave.randomFill(fillPurcentage);
        for (int i = 0; i < 25; i++){
            cave.filtering();
        }
        for (int i = 0; i < 15; i++){
                cave.additiveFiltering();
        }
        cave.placeWall();
        map = cave.getMapcave() ;
    }

    public void display(GraphicsContext gc) {
        Image sprite;
        final double height = HEIGHT / lines;
        final double width = WIDTH / columns;
        for (int column = 0; column < columns; column++ ) {
            for (int line = 0; line < lines; line++) {
                switch(map[column][line]) {
                    case 25: sprite = red; break;
                    case 0: sprite = ground; break;
                    case 1: sprite = voidImage; break;
                    case 2: sprite = nWall; break;
                    case 3: sprite = wWall; break;
                    case 4: sprite = nwWall; break;
                    case 6: sprite = sWall; break;
                    case 8: sprite = swWall; break;
                    case 12: sprite = eWall; break;
                    case 13: sprite = neWall; break;
                    case 17: sprite = seWall; break;
                    default: sprite = voidImage;
                }
                gc.drawImage(sprite, column * width, line * height,width,height);
            }
        }
    }
}