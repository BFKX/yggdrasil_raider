package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;

public class BasicMonster extends Monster
{
    private double positionX;
    private double positionY;
    private int directionX;
    private int directionY;
    private boolean isAttacked;
    private int[][] map;
    private double type;
    final private double SIDE = HEIGHT / 60;
    private int etas ;
    private double toCharactersDistance;
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
        hitbox.setRadius(2 * RADIUS);
        this.etas = 0 ;
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
    gc.drawImage(sprite, (this.position.getX() - xOffset) * SIDE - RADIUS, (this.position.getY() - yOffset) * SIDE - RADIUS, 2 * RADIUS, 2 * RADIUS);
    gc.drawImage(lifeBar.get(this.healthPoint), (this.position.getX() - xOffset) * SIDE - RADIUS, (this.position.getY() - yOffset) * SIDE - 13 - RADIUS, 2 * RADIUS, 0.25 * RADIUS);
    hitbox.draw(gc, mainCharactersPosition);
}
public void updateDisplacement() {

    if(etas == 0){
        double toCharactersDistance = position.distance(mainCharactersPosition);
        if ( toCharactersDistance < 500 ){
            etas = ThreadLocalRandom.current().nextInt(0 ,100 ) > 60 ? 1: 2;
        }
    }
    if(etas == 1 ){
        toCharactersDistance = position.distance(mainCharactersPosition);
        if ( toCharactersDistance < 200 ){
        double Xdist = position.getX() - mainCharactersPosition.getX();
        double Ydist = position.getY() - mainCharactersPosition.getY();
        directionX = Xdist > 0 ? -1 : (Xdist < 0)  ?  1 : 0  ;
        directionY = Ydist > 0 ? -1 : (Ydist < 0)  ?  1 : 0  ;
        }
    }
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
