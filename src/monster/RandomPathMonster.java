package monster;

import mapping.Map;
import tools.Coordinate;
import java.awt.*;

public class RandomPathMonster extends Monster {
    private final double RADIUS = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;

    public RandomPathMonster(Coordinate coordinate,Map map ){
        super(coordinate,map);
        directionX = 1 ; directionY = 1 ;
        speedX = RADIUS /10 ; speedY = RADIUS /10 ;
        speedLimitX = RADIUS /10; speedLimitY = RADIUS /10;
    }

    public void updateDeplacement(){
        int[][] mapint = map.getMap();
        if((int)(position.getX()+speedX) < 0 ||(int)(position.getX()+speedX) > mapint.length -1
                ||((int)position.getY()+speedY) < 0 || (int)position.getY()+speedY > mapint[0].length -1
                || mapint[(int)(position.getX()+speedX)][(int)(position.getY()+speedY)] >0 ){
            directionX = -directionX ;
            directionY = -directionY ;
            speedX = speedX / 2 ;
            speedY = speedY / 2 ;
            System.out.println("mur : "+ (position.getX() + speedX) +","+ (position.getY()+speedY));
            System.out.println(speedX + ";" + speedY );
        }/*else {
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
        }*/
        if(Math.abs(speedX) < speedLimitX){
            speedX = speedX + directionX * speedLimitX / 13;
        }
        if(Math.abs(speedY)< speedLimitY){
            speedY = speedY + directionY * speedLimitY/13;
        }

        update();
    }
}
