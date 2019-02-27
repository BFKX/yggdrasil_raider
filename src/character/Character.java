package character;

import tools.Coordinate;
import tools.Hitbox;
import javafx.scene.input.KeyCode;

import java.awt.Toolkit;


public class Character {

    private Coordinate position;
    private Hitbox hitbox;
    private final double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 40;
    private final double width = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 40;

    public Character(Coordinate position, Hitbox hitbox) {

        this.position = position;
        this.hitbox = new Hitbox(position, width, height);
    }

    public void attack() {

        Hitbox att = new Hitbox(new Coordinate(position.getX() + width / 2, position.getY() + height + 20 ), 10, 10 );
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
