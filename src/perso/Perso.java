package perso;

import tools.Coordinate;
import tools.Hitbox;

public class Perso {
    private Coordinate position ;
    private Hitbox hitbox ;

    public Perso(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = hitbox;
    }
}
