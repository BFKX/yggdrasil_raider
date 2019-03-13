package character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import tools.Hitbox;
import javafx.scene.input.KeyCode;

import java.awt.Toolkit;


public class Character {

    private Coordinate position;
    private Hitbox hitbox;
    final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 40;
    final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 40;
    final private Image image = new Image("resources/images/ground.jpg");

    public Character(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position, WIDTH, HEIGHT);
    }

    public void attack() {

        Hitbox att = new Hitbox(new Coordinate(position.getX() + WIDTH / 2, position.getY() + HEIGHT + 20 ), 10, 10 );
    }

    private void validatePosition(){
    }

    public void displacement(KeyCode code){
        switch(code) {
            case LEFT:  position.setX(position.getX() - 40); break;
            case RIGHT: position.setX(position.getX() + 40); break;
            case UP:    position.setY(position.getY() - 40); break;
            case DOWN:  position.setY(position.getY() + 40); break;
            default: break;
        }
    }

    public void displayCharacter(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
