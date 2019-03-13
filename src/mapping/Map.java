package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.Toolkit;

public class Map {
    private int[][] map;
    private Case[][] mapCases;
    private int lines;
    private int columns;
    private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private Image ground = new Image("resources/images/ground.jpg");

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

    public void creatCave(int fillPercentage) {
        Cave cave = new Cave(columns, lines);
        cave.randomFill(fillPercentage);
        for (int i = 0; i < 15; i++){
            cave.filtering();
        }
        //cave.coloring();
        map = cave.getMapcave() ;

    }

    public void addGround() {
        for (int column = 0; column < columns; column++ ) {
            addCase(column,lines - 1, 1, new Image("resources/images/ground.jpg"));
        }
    }

    public void display(GraphicsContext gc) {
        for (int column = 0; column < columns; column++ ) {
            for (int line = 0; line < lines; line++) {
                if(map[column][line] == 0) {
                    gc.setFill(Color.BLACK);
                } else if(map[column][line] == 1){
                    gc.drawImage(ground, WIDTH * column / columns,HEIGHT * line / lines,WIDTH / columns,HEIGHT / lines);
                }
            }
        }
    }
}