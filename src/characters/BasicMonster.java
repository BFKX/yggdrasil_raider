package characters;

import data_structures.ImageSet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import tools.Coordinate;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class BasicMonster extends Monster {
    private int directionX;
    private int directionY;
    private final int[][] map;
    private final double SIDE = HEIGHT / 60;
    private ImageSet midLifeSprites;
    private Image midLifeWaiting;

    public BasicMonster(Coordinate coordinate, Coordinate mainCharacterPosition, int[][] map) {
        super(coordinate,mainCharacterPosition);
        this.map = map;
        type = ThreadLocalRandom.current().nextInt(1, 4);

        HashMap<Integer, Image> images = new HashMap<>();
        images.put(0, new Image("resources/images/monsterWithHeadType_" + type + "_Waiting.png"));
        images.put(1, new Image("resources/images/monsterWithHeadType_" + type + "_LeftHand_1.png"));
        images.put(2, new Image("resources/images/monsterWithHeadType_" + type + "_LeftHand_2.png"));
        images.put(11, new Image("resources/images/monsterWithHeadType_" + type + "_RightHand_1.png"));
        images.put(12, new Image("resources/images/monsterWithHeadType_" + type + "_RightHand_2.png"));
        int[] sequence = {0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1, 0, 0, 0, 11, 11, 11, 12, 12, 12, 11, 11, 11};
        sprites = new ImageSet(images, sequence);
        images = new HashMap<>();
        images.put(0, new Image("resources/images/monsterWithoutHeadType_" + type + "_Waiting.png"));
        images.put(1, new Image("resources/images/monsterWithoutHeadType_" + type + "_LeftHand_1.png"));
        images.put(2, new Image("resources/images/monsterWithoutHeadType_" + type + "_LeftHand_2.png"));
        images.put(11, new Image("resources/images/monsterWithoutHeadType_" + type + "_RightHand_1.png"));
        images.put(12, new Image("resources/images/monsterWithoutHeadType_" + type + "_RightHand_2.png"));
        midLifeSprites = new ImageSet(images, sequence);

        waiting = new Image("resources/images/monsterWithHeadType_" + type + "_Waiting.png");
        midLifeWaiting = new Image("resources/images/monsterWithoutHeadType_" + type + "_Waiting.png");

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
        gc.save();
        double xOffset = characterPosition.getX() - WIDTH / (2 * SIDE);
        double yOffset = characterPosition.getY() - HEIGHT / (2 * SIDE);
        Image localWaiting;
        ImageSet localSprites;
        if (healthPoint > 50) {
            localSprites = sprites;
            localWaiting = waiting;
        } else {
            localSprites = midLifeSprites;
            localWaiting = midLifeWaiting;
        }
        angleSelector(gc, xOffset, yOffset);
        gc.drawImage((Math.abs(speedY) < 1 && Math.abs(speedX) < 1) ? localWaiting : localSprites.get(),
                (this.position.getX() - xOffset) * SIDE - RADIUS / 2,
                (this.position.getY() - yOffset) * SIDE - RADIUS / 2,
                RADIUS,
                RADIUS);
        gc.restore();
        gc.drawImage(healthBar.get(this.healthPoint), (this.position.getX() - xOffset) * SIDE - RADIUS * 0.375,
                (this.position.getY() - yOffset) * SIDE - 0.75 * RADIUS, 0.75 * RADIUS, 0.2 * RADIUS);
    }

    public void updateDisplacement() {
        if(state == 0) {
            double toCharactersDistance = position.distance(mainCharactersPosition);
            if (toCharactersDistance < 500)
                state = ThreadLocalRandom.current().nextInt(0, 100) > 60 ? 1 : 2;
        }
        if (state == 1 )
            if (position.distance(mainCharactersPosition) < 40){
                double xDist = position.getX() - mainCharactersPosition.getX();
                double yDist = position.getY() - mainCharactersPosition.getY();
                directionX = (xDist >= 0 ) ? -1 : (xDist < -1) ? 1 : 0;
                directionY = (yDist >= 0) ? -1 : (yDist < -1) ? 1 : 0;
            }

        if (collision(position, map)) {
            directionX = -directionX;
            directionY = -directionY;
            speedX = directionX * speedLimitX / 13;
            speedY = directionY * speedLimitY / 13;
        }
        if (!knockBack) {
            speedX = (Math.abs(speedX) < speedLimitX) ? speedX + directionX * speedLimitX / 13 : directionX*speedLimitX;
            speedY = (Math.abs(speedY) < speedLimitY) ? speedY + directionY * speedLimitY / 13 : directionY*speedLimitY;
        }else {
            state = 1;
            if ( Math.abs(speedX) > speedLimitX )
                speedX = speedX / 1.2 ;
            if ( Math.abs(speedY) > speedLimitY )
                speedY= speedY / 1.2 ;
            if (Math.abs(speedX) < speedLimitX && Math.abs(speedY) < speedLimitY)
                knockBack = false;
        }
    }
}
