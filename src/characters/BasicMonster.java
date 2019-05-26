package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;

import java.util.concurrent.ThreadLocalRandom;

public class BasicMonster extends Monster {
    private int directionX;
    private int directionY;
    private int[][] map;
    private final double SIDE = HEIGHT / 60;
    private int state = 0;
    private Image sprite;


    public BasicMonster(Coordinate coordinate, Coordinate mainCharacterPosition, int[][] map) {
        super(coordinate,mainCharacterPosition);
        this.map = map;
        type = ThreadLocalRandom.current().nextInt(1, 4);
        sprite = (type == 1) ? new Image("resources/images/monster1.png") : (type == 2) ?
                new Image("resources/images/monster2.png") : new Image("resources/images/monster3.png");
        healthPoint = 100;
        RADIUS = 5 * SIDE;
        directionX = ThreadLocalRandom.current().nextInt(-1, 2);
        directionY = ThreadLocalRandom.current().nextInt(-1, 2);
        speedX = directionX * RADIUS / 8;
        speedY = directionY * RADIUS / 8;
        speedLimitX = RADIUS / 32;
        speedLimitY = RADIUS / 32;
        healthBar.put(100, new Image("images/monsterHealthBar0.png"));
        healthBar.put(90, new Image("images/monsterHealthBar1.png"));
        healthBar.put(80, new Image("images/monsterHealthBar2.png"));
        healthBar.put(70, new Image("images/monsterHealthBar3.png"));
        healthBar.put(60, new Image("images/monsterHealthBar4.png"));
        healthBar.put(50, new Image("images/monsterHealthBar5.png"));
        healthBar.put(40, new Image("images/monsterHealthBar6.png"));
        healthBar.put(30, new Image("images/monsterHealthBar7.png"));
        healthBar.put(20, new Image("images/monsterHealthBar8.png"));
        healthBar.put(10, new Image("images/monsterHealthBar9.png"));
        hitbox.setRadius(RADIUS);
    }

    @Override
    public void display(GraphicsContext gc, Coordinate characterPosition) {
        double xOffset = characterPosition.getX() - WIDTH / (2 * SIDE);
        double yOffset = characterPosition.getY() - HEIGHT / (2 * SIDE);

        gc.drawImage(sprite, (this.position.getX() - xOffset) * SIDE - RADIUS / 2,
                (this.position.getY() - yOffset) * SIDE - RADIUS / 2, RADIUS, RADIUS);
        gc.drawImage(healthBar.get(this.healthPoint), (this.position.getX() - xOffset) * SIDE - RADIUS * 0.375,
                (this.position.getY() - yOffset) * SIDE - 0.75 * RADIUS, 0.75 * RADIUS, 0.2 * RADIUS);
        hitbox.draw(gc, mainCharactersPosition);
    }

    public void updateDisplacement() {

        if(state == 0) {
            double toCharactersDistance = position.distance(mainCharactersPosition);
            if (toCharactersDistance < 500) {
                state = ThreadLocalRandom.current().nextInt(0, 100) > 60 ? 1 : 2;
            }
        }
        if (state == 1 ) {
            if (position.distance(mainCharactersPosition) < 40){
            double xDist = position.getX() - mainCharactersPosition.getX();
            double yDist = position.getY() - mainCharactersPosition.getY();
            directionX = (xDist > 0) ? -1 : (xDist < 0) ? 1 : 0;
            directionY = (yDist > 0) ? -1 : (yDist < 0) ? 1 : 0;
            }
        }

        if (collision(position, map)) {
            directionX = -directionX;
            directionY = -directionY;
            speedX = directionX * speedLimitX / 13;
            speedY = directionY * speedLimitY / 13;
        }
        if ( !knockBack) {
            speedX = (Math.abs(speedX) < speedLimitX) ? speedX + directionX * speedLimitX / 13 : directionX*speedLimitX;
            speedY = (Math.abs(speedY) < speedLimitY) ? speedY + directionY * speedLimitY / 13 : directionY*speedLimitY;
        }else {
            if ( Math.abs(speedX) > speedLimitX )
                speedX = speedX / 1.2 ;
            if ( Math.abs(speedY) > speedLimitY )
                speedY= speedY / 1.2 ;
            if ( speedX < speedLimitX && speedY < speedLimitY){
                knockBack = false;
            }
        }
        }
}
