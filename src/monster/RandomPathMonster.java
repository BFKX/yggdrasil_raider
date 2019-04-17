package monster;

import mapping.Map;
import tools.Coordinate;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPathMonster extends Monster {
    private double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    public RandomPathMonster(Coordinate coordinate){
        super(coordinate);
        directionX = 1 ; directionY = 1 ;
        speedX = 0 ; speedY = 0 ;
        speedLimitX = SIDE * 15; speedLimitY = SIDE * 15;
    }

    public void updatedeplacement(Map map){
        int[][] mapint = map.getMap();
        if(mapint[(int)(position.getX()+speedX)][(int)(position.getY()+speedY)] >0 ){
            speedX = 0 ;
            speedY = 0 ;
            directionX = -directionX ;
            directionY = -directionY ;
        }else {
            if(ThreadLocalRandom.current().nextInt(0,100 ) > 99  ){
                directionX = - directionX ;
                directionY = -  directionY ;
            }
        }
        if(speedX < speedLimitX ){
            speedX = speedX + directionX * speedLimitX / 13;
        }
        if(speedY< speedLimitY){
            speedY = speedY + directionY * speedLimitY/13;
        }
    }
}
