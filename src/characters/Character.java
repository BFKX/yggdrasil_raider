package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;

import mapping.Map;
import data_structures.ImageSet;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Character {
    Map map;
    final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int type; // type == 0 => mainCharacter
    Coordinate position; // position en (double) case
    final Hitbox hitbox;
    double speedX = 0, speedY = 0; // en pixel
    double speedLimitX, speedLimitY; // en pixel
    double RADIUS; //en pixel
    final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60; //en pixel
    ImageSet sprites;
    Image waiting;
    final HashMap<Integer, Image> healthBar = new HashMap<>();
    int healthPoint;
    int angle;

    Character(Coordinate position) {
        this.position = position;
        hitbox = new Hitbox(position, RADIUS);
    }

    public Coordinate getPosition(){
        return this.position;
    }

    public void startPosition(Room room){
        int[][] map = room.getMap();
        int mid1, mid2;
         do {
            mid1 = ThreadLocalRandom.current().nextInt(map.length - (int)( map.length * 0.8), map.length);
            mid2 = ThreadLocalRandom.current().nextInt(map[0].length - (int)( map[0].length * 0.8), map[0].length);
        } while (map[mid1][mid2] > 0);
        this.position.setX(mid1);
        this.position.setY(mid2);
    }
    public void startPosition(Room room,Coordinate MainCharacterPosition){// for monsters
        int[][] map = room.getMap();
        int mid1, mid2;
        do {
            mid1 = ThreadLocalRandom.current().nextInt(map.length - (int)( map.length * 0.8), map.length);
            mid2 = ThreadLocalRandom.current().nextInt(map[0].length - (int)( map[0].length * 0.8), map[0].length);
        } while (map[mid1][mid2] > 0|| MainCharacterPosition.distance(new Coordinate(mid1,mid2)) <20);
        this.position.setX(mid1);
        this.position.setY(mid2);
    }

    public void display(GraphicsContext gc) {
        gc.save();
        if (type != 0) {
            gc.drawImage(healthBar.get(this.healthPoint), this.position.getX() * map.getSIDE() - 30,
                    this.position.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
        }
        angleSelector(gc);
        gc.drawImage((Math.abs(speedY) < 1 && Math.abs(speedX) < 1) ? waiting : sprites.get(), WIDTH / 2 - RADIUS / 2,
                HEIGHT / 2 - RADIUS / 2, RADIUS, RADIUS);
        gc.restore();
    }

    private void angleSelector(GraphicsContext gc) {
        if (speedY > 1 && speedX > 1) {
            angle = 135;
        } else if (speedY > 1 && speedX < -1) {
            angle = -135;
        } else if (Math.abs(speedY) < 1 && speedX > 1) {
            angle = 90;
        } else if (Math.abs(speedY) < 1 && speedX < -1) {
            angle = -90;
        } else if (speedY < -1 && speedX > 1) {
            angle = 45;
        } else if (speedY < -1 && speedX < -1) {
            angle = -45;
        } else if (speedY < -1 && Math.abs(speedX) < 1) {
            angle = 0;
        } else if (speedY > 1 && Math.abs(speedX) < 1) {
            angle = 180;
        }
        if (type == 0) {
            Rotate r = new Rotate(angle, WIDTH / 2, HEIGHT / 2);
            gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        }
    }

    int sign(double x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }

    boolean collision(Coordinate positionInt,int[][]mapInt) {
        int signSpeedX = sign(speedX);
        int signSpeedY = sign(speedY);
        int i, j;
        for (int k = -1; k < 2; k++) {
            i = (type == 0) ? (int) positionInt.getX() + (int) (signSpeedX * RADIUS / (2 * SIDE)) :
                    (int) positionInt.getX() + (int) (signSpeedX * RADIUS / SIDE);
            j = (type == 0) ? (int) positionInt.getY() + (int) (signSpeedY * RADIUS / (2 * SIDE)) :
                    (int) positionInt.getY() + (int) (signSpeedY * RADIUS / SIDE);
            if (i >= 0 && i < mapInt.length) {
                if (j >= 0 && j < mapInt[0].length) {
                    if (mapInt[i][j] > 0) {
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

    public void setMap(Map map) {
        this.map = map;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
}
