package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.CharacterActions;
import tools.Coordinate;
import mapping.Map;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import tools.Hitbox;
public class MonsterTest extends Monster
{
    private Hitbox hitbox;
    private int lifeValue;
    private int directionX;
    private int directionY;
    private boolean isAttacked;
    private Map map;
    private double type;
public MonsterTest(Coordinate coordinate, Coordinate mainCharacterPosition, Map map)
{
    super(coordinate,map,mainCharacterPosition);
    this.position.setX(positionInt.getX());
    this.position.setY(positionInt.getY());
    this.map = map;
    this.type = ThreadLocalRandom.current().nextInt(1, 4);
    this.lifeValue = 1000;
    RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    directionX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
    directionY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
    speedX = directionX * RADIUS / 8;
    speedY = directionY * RADIUS / 8;
    speedLimitX = RADIUS / 4 ;
    speedLimitY = RADIUS / 4;
    lifeBar.put(1000, new Image("resources/images/longue de vie 5.png"));
    lifeBar.put(800, new Image("resources/images/longue de vie 4.png"));
    lifeBar.put(600, new Image("resources/images/longue de vie 3.png"));
    lifeBar.put(400, new Image("resources/images/longue de vie 2.png"));
    lifeBar.put(200, new Image("resources/images/longue de vie 1.png"));
    lifeBar.put(0, new Image("resources/images/longue de vie 0.png"));
}
public void display(GraphicsContext gc, @NotNull Coordinate characterPosition) {
    double RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    double positionXX = characterPosition.getX();
    double positionYY = characterPosition.getY();
    double xoffset = positionXX - map.getWIDTH() / (2 * map.getSIDE());
    double yoffset = positionYY - map.getHEIGHT() / (2 * map.getSIDE());
    Image sprite = new Image("resources/images/monster1.png");
    if (this.type == 2) {
        sprite = new Image("resources/images/monster2.png");
    } else if (this.type == 3) {
        sprite = new Image("resources/images/monster3.png");
    }
    gc.drawImage(sprite, (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE(), 2 * RADIUS, 2 * RADIUS);
    gc.drawImage(lifeBar.get(lifeValue), (this.positionInt.getX() - xoffset) * map.getSIDE(), (this.positionInt.getY() - yoffset) * map.getSIDE() - 13, 2 * RADIUS, 0.25 * RADIUS);
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
public void valueOflife(@NotNull HashMap<CharacterActions, Boolean> inputs) // l'attaque "A"
  {
     if(inputs.get(CharacterActions.ATTACK) && Math.sqrt(this.hitbox.getOrigin().distance(new Coordinate( Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 + RADIUS/2,Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 + RADIUS/2))) < 2 * RADIUS )
     {
         this.lifeValue -= 200;
     }
  }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
