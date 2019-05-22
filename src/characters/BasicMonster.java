package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import tools.Hitbox;

public class BasicMonster extends Monster
{
    private Hitbox hitbox;
    private double positionX;
    private double positionY;
    private int directionX;
    private int directionY;
    private boolean isAttacked;
    private int[][] map;
    private double type;
    final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    final private double SIDE = HEIGHT / 60;

    public BasicMonster (Coordinate coordinate, Coordinate mainCharacterPosition, int[][] map) {
        super(coordinate,mainCharacterPosition);
        this.positionX = position.getX();
        this.positionY = position.getY();
        this.type = ThreadLocalRandom.current().nextInt(1, 4);
        this.healthPoint = 1000;
        this.map = map;
        RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
        directionX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        directionY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        speedX = directionX * RADIUS / 8;
        speedY = directionY * RADIUS / 8;
        speedLimitX = RADIUS / 32;
        speedLimitY = RADIUS / 32;
        lifeBar.put(1000, new Image("images/lifebar5.png"));
        lifeBar.put(800, new Image("images/lifebar4.png"));
        lifeBar.put(600, new Image("images/lifebar3.png"));
        lifeBar.put(400, new Image("images/lifebar2.png"));
        lifeBar.put(200, new Image("images/lifebar1.png"));
        lifeBar.put(0, new Image("images/lifebar0.png"));
    }

@Override
public void display(GraphicsContext gc, Coordinate characterPosition) {
    double xOffset = characterPosition.getX() - WIDTH/ (2 * SIDE);
    double yOffset = characterPosition.getY() - HEIGHT / (2 * SIDE);
    Image sprite = new Image("resources/images/monster1.png");
    if (this.type == 2) {
        sprite = new Image("resources/images/monster2.png");
    } else if (this.type == 3) {
        sprite = new Image("resources/images/monster3.png");
    }
    gc.drawImage(sprite, (this.position.getX() - xOffset) * SIDE, (this.position.getY() - yOffset) * SIDE, 2 * RADIUS, 2 * RADIUS);
    gc.drawImage(lifeBar.get(healthPoint), (this.position.getX() - xOffset) * SIDE, (this.position.getY() - yOffset) * SIDE - 13, 2 * RADIUS, 0.25 * RADIUS);
    this.hitbox = new Hitbox(new Coordinate(this.position.getX() + RADIUS / SIDE,this.position.getY() + RADIUS / SIDE),2 * RADIUS);
    hitbox.draw(gc, mainCharactersPosition);
}
public void updateDisplacement() {
    //double toCharactersDistance = position.distance(mainCharactersPosition);
    //if(toCharactersDistance < 500) {
    //    double Xdist = position.getX() - mainCharactersPosition.getX();
    //    double Ydist = position.getY() - mainCharactersPosition.getY();
    //    directionX = Xdist > 0 ? 1 : (Xdist < 0)  ?  -1 : 0  ;
    //    directionY = Ydist > 0 ? 1 : (Ydist < 0)  ?  -1 : 0  ;
    //    speedX =  directionX * speedLimitX / 13 ;
    //    speedY = directionY *  speedLimitY / 13 ;
    //}
    if (collision(position, map)) {
        directionX = -1 * directionX;
        directionY = -1 * directionY;
        speedX = directionX * speedLimitX / 13;
        speedY = directionY * speedLimitY / 13;
    }

    speedX = Math.abs(speedX) < speedLimitX ? speedX + directionX * speedLimitX / 13 : directionX * speedLimitX; // var = test ? vrai : faux
    speedY = Math.abs(speedY) < speedLimitY ? speedY + directionY * speedLimitY / 13 : directionY * speedLimitY;
}
}