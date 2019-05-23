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

    public BasicMonster (Coordinate coordinate, Coordinate mainCharacterPosition, int[][] map) {
        super(coordinate,mainCharacterPosition);
        this.positionX = position.getX();
        this.positionY = position.getY();
        this.type = ThreadLocalRandom.current().nextInt(1, 4);
        this.healthPoint = 100;
        this.map = map;
        RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
        directionX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        directionY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        speedX = directionX * RADIUS / 8;
        speedY = directionY * RADIUS / 8;
        speedLimitX = RADIUS / 32;
        speedLimitY = RADIUS / 32;
        healthBar.put(100, new Image("images/healthBar0.png"));
        healthBar.put(90, new Image("images/healthBar1.png"));
        healthBar.put(80, new Image("images/healthBar2.png"));
        healthBar.put(70, new Image("images/healthBar3.png"));
        healthBar.put(60, new Image("images/healthBar4.png"));
        healthBar.put(50, new Image("images/healthBar5.png"));
        healthBar.put(40, new Image("images/healthBar6.png"));
        healthBar.put(30, new Image("images/healthBar7.png"));
        healthBar.put(20, new Image("images/healthBar8.png"));
        healthBar.put(10, new Image("images/healthBar9.png"));
        hitbox.setRadius(2 * RADIUS);
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
    gc.drawImage(healthBar.get(healthPoint), (this.position.getX() - xOffset) * SIDE - 0.75 * RADIUS, (this.position.getY() - yOffset) * SIDE - 1.5 * RADIUS, 1.5 * RADIUS, 0.41 * RADIUS);
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
