package perso;

import tools.Coordinate;
import tools.Hitbox;

public class Perso {
    private Coordinate position ;
    private Hitbox hitbox ;
    private double Height ;
    private double Width ;

    public Perso(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position , Height , Width);
    }
    public void Attaque() {
        Hitbox att = new Hitbox( new Coordinate(position.getX() + Height / 2 , position.getY() + Width + 20 ) , 10 ,10 ) ;  // cree une hitbox  devan le perso
        System.out.println();
    }


}
