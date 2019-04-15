package monster;

import tools.Coordinate;
import tools.Hitbox;

import java.awt.*;
import mapping.Map;
public class Monster {
    private Coordinate position;
    private final Hitbox hitbox;
    private double speedX = 0, speedY = 0;
    final private double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    final private double speedLimitX = SIDE * 15;
    final private double speedLimitY = SIDE * 15;
    public Monster(){
        this.position = new Coordinate(SIDE * 30, SIDE * 30 );
        this.hitbox = new Hitbox(position, SIDE);
    }
    public void deplacement(Map map){
        int[][] mapint = map.getMap();
        if(mapint[(int)(position.getX()+speedX)][(int)(position.getY()+speedY)] >0 ){
            speedX = - speedX ;
            speedY = - speedY ;
        }
    }

    private void update(){
        this.position.add(speedX,speedY);
    }

}
