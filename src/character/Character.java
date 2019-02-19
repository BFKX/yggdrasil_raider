package character;

import tools.Coordinate;
import tools.Hitbox;

public class Character {
    private Coordinate position ;
    private Hitbox hitbox ;
    private double Height ;
    private double Width ;

    public Character(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position , Height , Width);
    }
    public void Attaque() {
        Hitbox att = new Hitbox( new Coordinate(position.getX() + Height / 2 , position.getY() + Width + 20 ) , 10 ,10 ) ;  // cree une hitbox  devan le character
        System.out.println();
    }


}
