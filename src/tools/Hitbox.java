package tools;

public class Hitbox {
    private Coordinate origine ;
    private double width ;

    public Hitbox(Coordinate origine, double width) {
        this.origine = origine;
        this.width = width;
    }

    public boolean collide (Hitbox hitbox) {
        if ( this.origine.getX() - hitbox.origine.getX() < hitbox.width && this.origine.getY() - hitbox.origine.getY() < hitbox.height && hitbox.origine.getX() - this.origine.getX() < this.width && hitbox.origine.getY() - this.origine.getY() < this.height)
            return true ;
        return false ;
    }

}
