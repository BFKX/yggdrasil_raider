package characters;

import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;

public class Projectile {
    private Hitbox hitbox;
    private double speedX;
    private double speedY;
    private Coordinate position;
    private double damages;
    private Room room;
    private int [][] map;

    public Projectile(Coordinate position , double speedX , double speedY,Room room) {
        this.speedX = speedX;
        this.speedY = speedY;
        this.position = position;
        this.room = room;
        this.map =  room.getMap();
    }

    public boolean Coolide(Hitbox other){
        return  this.hitbox.collide(other);
    }
    public boolean update(){
        if( map[(int) this.position.getX() + (int) speedX] [(int) this.position.getY() + (int)speedY] >0 ){
            return false;
        }
        else {
            this.position.add(speedX,speedY);
            return true;
        }
    }
    private double getDamages(){return damages;}
}
