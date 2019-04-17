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
        speedLimitX = SIDE /10; speedLimitY = SIDE /10;
    }
    public void updateDeplacement(Map map){
        int[][] mapint = map.getMap();
        if((int)(position.getX()+speedX) < 0 ||(int)(position.getX()+speedX) > mapint.length
                ||((int)position.getY()+speedY) < 0 || (int)position.getY()+speedY > mapint[0].length -1
                || mapint[(int)(position.getX()+speedX)][(int)(position.getY()+speedY)] >0 ){
            directionX = -directionX ;
            directionY = -directionY ;
            System.out.println("mure");
        }else {
            if(ThreadLocalRandom.current().nextInt(0,100 ) > 99  ) {
                speedX = -speedX;
                directionX = -directionX;
                System.out.print("changex");
            }
            if(ThreadLocalRandom.current().nextInt(0,100 ) > 99  ){
                speedY = -speedY ;
                directionY = -directionY ;
                System.out.print("chagementY");
            }
        }
        if(speedX < speedLimitX ){
            speedX = speedX + directionX * speedLimitX / 13;
        }
        if(speedY< speedLimitY){
            speedY = speedY + directionY * speedLimitY/13;
        }
        update();
    }

}
