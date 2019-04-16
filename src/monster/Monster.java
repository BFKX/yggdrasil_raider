package monster;

import tools.Coordinate;
import tools.Hitbox;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import mapping.Map;
public class Monster {
    protected Coordinate position;
    protected final Hitbox hitbox;
    protected double speedX , speedY ;
    protected int directionX ;
    protected  int directionY ;
    private double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
    protected double speedLimitX ;
    protected double speedLimitY ;
    public Monster(Coordinate coordinate){
        this.position = coordinate ;
        this.hitbox = new Hitbox(position, SIDE);
    }

    private void update(){
        this.position.add(  speedX ,  speedY);
    }



}
