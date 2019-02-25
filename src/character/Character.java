package character;

import tools.Coordinate;
import tools.Hitbox;
import javafx.scene.input.KeyCode;


public class Character {
    private Coordinate position ;
    private Hitbox hitbox ;
    private double height ;
    private double width ;

    public Character(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position , height , width);
    }
    public void attaque() {
        Hitbox att = new Hitbox( new Coordinate(position.getX() + height / 2 , position.getY() + width + 20 ) , 10 ,10 ) ;  // cree une hitbox  devan le character
        System.out.println();
    }
    public void validatePosition(){
    }

    public void displacement(KeyCode code){
        switch(code) {
            case LEFT:  position.setX(position.getX() - 5); break;
            case RIGHT: position.setX(position.getX() + 5); break;
            case UP:    position.setY(position.getY() + 5); break;
            case DOWN:  position.setY(position.getY() - 5); break;
            default: break;

        }

    }
}
