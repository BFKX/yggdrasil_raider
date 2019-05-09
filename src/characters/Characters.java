package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.Coordinate;
import tools.Hitbox;

import mapping.Map;

import java.awt.*;
import java.util.HashMap;

public abstract class Characters {
    Map map;
    int[][] mapInt;
    Coordinate position;
    Coordinate positionInt;
    final Hitbox hitbox;
   public double speedX = 0, speedY = 0;
    double speedLimitX, speedLimitY;
    double RADIUS;
    final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    final HashMap<String, Image> imageSet = new HashMap<>();
    private String activeSprite = "movingEast";
    public int lifeValue;
    boolean isAttacking;

    Characters(Coordinate position, Map map ) {
        this.position = new Coordinate(20, 20);
        this.positionInt = new Coordinate(position.getX() / SIDE, position.getY() / SIDE);
        this.hitbox = new Hitbox(position, RADIUS);
        this.map = map;
        mapInt = map.getMap();
        isAttacking = false;
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
        return imageSet.get(activeSprite);
    }

    public void display(@NotNull GraphicsContext gc) {
        gc.drawImage(spriteSelector(), Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - RADIUS / 2,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - RADIUS / 2, RADIUS, RADIUS);
    }

    int signOf(double x) {
        if (x > 0) {
            return  1;
        } else if (x < 0) {
            return -1;
        }
        return 0;
    }

    boolean collision(Coordinate positionInt) {
        int signSpeedX = signOf(speedX);
        int signSpeedY = signOf(speedY);
        for (int k = -1; k < 2; k++) {
            int i = (int) positionInt.getX() + signSpeedX  ;
            int j = (int) positionInt.getY() + signSpeedY  ;
            if (i>= 0 && i < mapInt.length) {
                if (j >= 0 && j < mapInt[0].length) {
                    if (mapInt[i][j] > 0) {
                        mapInt[i][j] = 8000;
                        System.out.println("signe " + signSpeedX +';' + signSpeedY);
                        System.out.println("spped"+ speedX +","+speedY);
                        return true;
                    }
                    System.out.println(mapInt[(int) positionInt.getX() + signSpeedX][(int) positionInt.getY() + signSpeedY]);
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

    public void attackHitboxCreate(){
        Coordinate positionCase = new Coordinate(positionInt.getX(),positionInt.getY());
        double radius = RADIUS / 10;
        Hitbox hitbox = new Hitbox(positionCase,radius);
    }

    public void setMap(Map map) {
        this.map = map;
        this.mapInt = map.getMap();
    }
}
