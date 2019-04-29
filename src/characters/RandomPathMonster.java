package characters;

import mapping.Map;
import tools.Coordinate;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPathMonster extends Monster {
    public RandomPathMonster(Coordinate coordinate,Coordinate mainCharacterPosition,Map map ) {
        super(coordinate,map,mainCharacterPosition);
        RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
        directionX = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        directionY = (1 - 2 * ThreadLocalRandom.current().nextInt(0, 2));
        speedX = directionX * RADIUS / 8;
        speedY = directionY * RADIUS / 8;
        speedLimitX = RADIUS / 4 ;
        speedLimitY = RADIUS / 4;
    }

    public void updateDisplacement(){
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
            System.out.println(" value :" + mapInt[(int)positionInt.getX() + signSpeedX][(int)positionInt.getY() + signSpeedY] ) ;
            System.out.println("jumpt");
            directionX = -1 *  directionX;
            directionY = -1 * directionY;
            speedX = directionX * speedLimitX / 13;
            speedY = directionY * speedLimitY / 13;
        }

        speedX = Math.abs(speedX) < speedLimitX ? speedX + directionX * speedLimitX / 13 : directionX * speedLimitX; // var = test ? vrai : faux
        speedY = Math.abs(speedY) < speedLimitY ? speedY + directionY * speedLimitY / 13 : directionY * speedLimitY;
        update();
    }

}
