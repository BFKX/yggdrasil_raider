package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.Coordinate;
import mapping.Map;
import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import tools.Hitbox;
public class MonsterTest extends Monster
{
    private Hitbox hitbox;
    private double lifeValue = 1000.0;
    private double positionX;
    private double positionY;
    int directionX;
    int directionY ;
    Map map;
    private double type;
public MonsterTest(Coordinate coordinate,Coordinate mainCharacterPosition,Map map,double type)
{
    super(coordinate,map,mainCharacterPosition);
    this.positionX = positionInt.getX();
    this.positionY = positionInt.getY();
    this.map = map;
    this.hitbox = new Hitbox(coordinate,RADIUS);
    this.type = type;
    RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    directionX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
    directionY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
    speedX = directionX * RADIUS / 8;
    speedY = directionY * RADIUS / 8;
    speedLimitX = RADIUS / 4 ;
    speedLimitY = RADIUS / 4;
}
public void display(GraphicsContext gc, @NotNull Coordinate characterPosition)
{
    double RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    double positionXX = characterPosition.getX();
    double positionYY = characterPosition.getY();
    double xoffset =  positionXX - map.getwidth() / (2 * map.getSIDE());
    double yoffset =  positionYY - map.getheight() / (2 * map.getSIDE());
    if(this.type <= 1) {
        gc.drawImage(new Image("resources/images/monster1.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE(), 2 * RADIUS, 1.5 * RADIUS);
        gc.drawImage(new Image("resources/images/longue de vie 5.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE() - 13, 2 * RADIUS, 0.25 * RADIUS);
    }
    else if(this.type > 1 && this.type <= 2)
    {
        gc.drawImage(new Image("resources/images/monster2.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE(), 2 * RADIUS, 2 * RADIUS);
        gc.drawImage(new Image("resources/images/longue de vie 5.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE() - 10, 2 * RADIUS, 0.25 * RADIUS);
    }
    else if(this.type > 2 && this.type <= 3)
    {
        gc.drawImage(new Image("resources/images/monster3.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE(), 2 * RADIUS, 2 * RADIUS);
        gc.drawImage(new Image("resources/images/longue de vie 5.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE() - 10, 2 * RADIUS, 0.25 * RADIUS);
    }
    else
    {
        gc.drawImage(new Image("resources/images/boss.png"), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE(), 8 * RADIUS, 8 * RADIUS);
        //boss
    }
    this.hitbox = new Hitbox(new Coordinate((this.positionInt.getX() - xoffset) * map.getSIDE(),(this.positionInt.getY() - yoffset) * map.getSIDE()),2 * RADIUS);
}
public void updateDisplacement()
{
    int[][] mapInt = map.getMap();
    int signSpeedX = signOf(speedX);
    int signSpeedY = signOf(speedY);
    double toCharactersDistance = positionInt.distance(mainCharactersPosition);
    if(toCharactersDistance < 500) {
        double Xdist = positionInt.getX() - mainCharactersPosition.getX();
        double Ydist = positionInt.getY() - mainCharactersPosition.getY();
        directionX = Xdist > 0 ? 1 : (Xdist < 0)  ?  -1 : 0  ;
        directionY = Ydist > 0 ? 1 : (Ydist < 0)  ?  -1 : 0  ;
        speedX =  directionX * speedLimitX / 13 ;
        speedY = directionY *  speedLimitY / 13 ;
    }
    if((int)positionInt.getX() + signSpeedX < 0 || (int)positionInt.getX() + signSpeedX > mapInt.length - 1 ||
            (int)positionInt.getY() + signSpeedY < 0 || (int)positionInt.getY() + signSpeedY > mapInt[0].length - 1 ||
            mapInt[(int)positionInt.getX() + signSpeedX][(int)positionInt.getY() + signSpeedY] > 0) {
        directionX = -1 *  directionX;
        directionY = -1 * directionY;
        speedX = directionX * speedLimitX / 13;
        speedY = directionY * speedLimitY / 13;
    }

    speedX = Math.abs(speedX) < speedLimitX ? speedX + directionX * speedLimitX / 13 : directionX * speedLimitX; // var = test ? vrai : faux
    speedY = Math.abs(speedY) < speedLimitY ? speedY + directionY * speedLimitY / 13 : directionY * speedLimitY;
    update();
}

public void drawhitbox(GraphicsContext gc){this.hitbox.draw(gc);}
public void valueOflife()
{

}

}
