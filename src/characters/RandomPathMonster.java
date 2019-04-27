package characters;

import mapping.Map;
import tools.Coordinate;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPathMonster extends Monster {
    private final double RADIUS = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;

    public RandomPathMonster(Coordinate coordinate,Map map ){
        super(coordinate,map);
        directionX = 1 ; directionY = -1 ;
        speedX = (1- 2* ThreadLocalRandom.current().nextInt(0,2))* RADIUS /8 ;
        speedY = (1- 2* ThreadLocalRandom.current().nextInt(0,2))* RADIUS /8 ;
        speedLimitX = RADIUS/4 ; speedLimitY = RADIUS/4 ;
    }

    public void updateDeplacement(){
        int[][] mapint = map.getMap();
        int signSpeedX = sign(speedX);
        int signSpeedY = sign(speedY);
        if((int) positionInt.getX()+signSpeedX < 0 || (int) positionInt.getX()+signSpeedX > mapint.length -1 ||
            (int) positionInt.getY() + signSpeedY <0 || (int) positionInt.getY() + signSpeedY > mapint[0].length -1 ||
            mapint[(int) positionInt.getX()+signSpeedX ][(int) positionInt.getY() + signSpeedY]>0 ){
            directionX = -1*  directionX;
            directionY = -1* directionY ;
            speedX = directionX * speedLimitX / 13;
            speedY = directionY * speedLimitY / 13;
        }
        if(Math.abs(speedX) < speedLimitX){
            speedX = speedX + directionX * speedLimitX / 13;
        }else {
            speedX = speedLimitX ;
        }
        if(Math.abs(speedY)< speedLimitY){
            speedY = speedY + directionY * speedLimitY/13;
        }else {
            speedY = speedLimitY ;
        }
        update();
    }



}
