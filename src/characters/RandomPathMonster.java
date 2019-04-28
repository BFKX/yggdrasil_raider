package characters;

import mapping.Map;
import tools.Coordinate;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPathMonster extends Monster {
    public RandomPathMonster(Coordinate coordinate,Map map ) {
        super(coordinate,map);
        RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
        directionX = 1;
        directionY = -1;
        speedX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2)) * RADIUS / 8;
        speedY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2)) * RADIUS / 8;
        speedLimitX = RADIUS / 4 ;
        speedLimitY = RADIUS / 4;
    }

    public void updateDisplacement(){
        int[][] mapInt = map.getMap();
        int signSpeedX = signOf(speedX);
        int signSpeedY = signOf(speedY);
        if((int)positionInt.getX() + signSpeedX < 0 || (int)positionInt.getX() + signSpeedX > mapInt.length - 1 ||
            (int)positionInt.getY() + signSpeedY < 0 || (int)positionInt.getY() + signSpeedY > mapInt[0].length - 1 ||
            mapInt[(int)positionInt.getX() + signSpeedX][(int)positionInt.getY() + signSpeedY] > 0) {
            directionX = -1 *  directionX;
            directionY = -1 * directionY;
            speedX = directionX * speedLimitX / 13;
            speedY = directionY * speedLimitY / 13;
        }

        speedX = Math.abs(speedX) < speedLimitX ? speedX + directionX * speedLimitX / 13 : speedLimitX; // var = test ? vrai : faux
        speedY = Math.abs(speedY) < speedLimitY ? speedY + directionY * speedLimitY / 13 : speedLimitY;

        update();
    }



}
