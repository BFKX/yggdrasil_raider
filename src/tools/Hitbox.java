package tools;

public class Hitbox {
    private Coordinate uL ;
    private Coordinate uR;
    private Coordinate bL ;
    private Coordinate bR ;

    public Hitbox(Coordinate uL, Coordinate uR, Coordinate bL, Coordinate bR) {
        this.uL = uL;
        this.uR = uR;
        this.bL = bL;
        this.bR = bR;
    }

    public boolean collide (Hitbox hitbox) {
        return ( this.origne

    }

}
