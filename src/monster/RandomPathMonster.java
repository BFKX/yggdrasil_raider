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
        speedX = SIDE /10 ; speedY = SIDE /10 ;
        speedLimitX = SIDE /10; speedLimitY = SIDE /10;
    }
    public void updateDeplacement(Map map){
        int[][] mapint = map.getMap();
        if((int)(position.getX()+speedX) < 0 ||(int)(position.getX()+speedX) > mapint.length -1
                ||((int)position.getY()+speedY) < 0 || (int)position.getY()+speedY > mapint[0].length -1
                || mapint[(int)(position.getX()+speedX)][(int)(position.getY()+speedY)] >0 ){
            //int rd = ThreadLocalRandom.current().nextInt(0,3);
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
        try{
            map.getMap()[(int) position.getX() + (int) speedX][(int) position.getY() + (int) speedY] = 900;
        }catch (IndexOutOfBoundsException e){
            System.out.println((int) position.getX() + (int) speedX+ "a,a" +(int) position.getY() + (int) speedY);
            System.out.println((map.getMap().length +";" + map.getMap()[0].length));
            System.out.println("non");
        }
        update();
    }
}
