package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;

import mapping.Map;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Character {
    Map map;
    final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int type; // type = 0 => perso principal
    Coordinate position; // position en (double ) case
    final Hitbox hitbox;
    double speedX = 0, speedY = 0; // en pixel
    double speedLimitX, speedLimitY; // en pixel
    double RADIUS; //en pixel
    final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60; //en pixel
    final HashMap<String, Image> spriteSet = new HashMap<>();
    final HashMap<Integer, Image> lifeBar = new HashMap<>();
    private String activeSprite = "movingEast";
    int healthPoint;
    boolean isAttacking;

    Character(Coordinate position) {
        this.position = position;
        this.hitbox = new Hitbox(position, RADIUS); // en case ( double )
        isAttacking = false;
    }

    public Coordinate getPosition(){
        return this.position;
    }

    public void startposition(Room room){
        int [][] map = room.getMap();
        int mid1 = ThreadLocalRandom.current().nextInt(map.length - (int)( map.length * 0.8), map.length);
        int mid2 = ThreadLocalRandom.current().nextInt(map[0].length - (int)( map[0].length * 0.8), map[0].length);
        while (map[mid1][mid2] > 0) {
            mid1 = ThreadLocalRandom.current().nextInt(map.length - (int)( map.length * .8)  , map.length );
            mid2 = ThreadLocalRandom.current().nextInt(map[0].length - (int)( map[0].length * .8), map[0].length );
        }
        this.position.setX(mid1);
        this.position.setY(mid2);
    }

    public void display(GraphicsContext gc) {
        if (type != 0) {
            gc.drawImage(lifeBar.get(healthPoint), this.position.getX() * map.getSIDE() - 30,
                    this.position.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
        } else {
            gc.drawImage(lifeBar.get(healthPoint), 0, HEIGHT * 0.95, WIDTH / 5, HEIGHT / 20);
        }
        gc.drawImage(spriteSelector(), WIDTH / 2 - RADIUS / 2, HEIGHT / 2 - RADIUS / 2, RADIUS, RADIUS);
    }

    private Image spriteSelector() {
        if (speedY > 1 && speedX > 1) {
            activeSprite = "movingSouthEast";
        } else if (speedY > 1 && Math.abs(speedX) < 1) {
            activeSprite = "movingSouth";
        } else if (speedY > 1 && speedX < -1) {
            activeSprite = "movingSouthWest";
        } else if (speedY < -1 && speedX > 1) {
            activeSprite = "movingNorthEast";
        } else if (Math.abs(speedY) < 1 && speedX > 1) {
            activeSprite = "movingEast";
        } else if (speedY < -1 && Math.abs(speedX) < 1) {
            activeSprite = "movingNorth";
        } else if (speedY < -1 && speedX < -1) {
            activeSprite = "movingNorthWest";
        } else if (Math.abs(speedY) < 1 && speedX < -1) {
            activeSprite = "movingWest";
        }
        return spriteSet.get(activeSprite);
    }

    int signOf(double x) {
        if (x > 0) {
            return  1;
        } else if (x < 0) {
            return -1;
        }
        return 0;
    }

    boolean collision(Coordinate positionInt,int[][]mapInt) {
        int signSpeedX = signOf(speedX);
        int signSpeedY = signOf(speedY);
        for (int k = -1; k < 2; k++) {
            int i = (int) positionInt.getX() + signSpeedX  ;
            int j = (int) positionInt.getY() + signSpeedY  ;
            if (i >= 0 && i < mapInt.length) {
                if (j >= 0 && j < mapInt[0].length) {
                    if (mapInt[i][j] > 0) {
                        mapInt[i][j] = 8000;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Coordinate matrixRotation(Coordinate originCharacter, Coordinate originAttack, double angle ){
        double xCenteredPosition = originAttack.getX() - originCharacter.getX();
        double yCenteredPosition = originAttack.getY() - originCharacter.getY();
        double rx = xCenteredPosition * Math.cos(angle) - yCenteredPosition *Math.sin(angle);
        double ry = xCenteredPosition * Math.sin(angle) + yCenteredPosition *Math.cos(angle);
        rx += originCharacter.getX();
        ry += originCharacter.getY();
        return new Coordinate(rx,ry);
    }

    public double getRADIUS(){
        return RADIUS;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void drawHitbox(GraphicsContext gc, MainCharacter mainCharacter) {
        if (type == 0) {
            hitbox.draw(gc, this.getPosition());
        } else {
            hitbox.draw(gc, mainCharacter.getPosition());
        }

    }
}
