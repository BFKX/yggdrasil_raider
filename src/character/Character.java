package character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.CharacterActions;
import tools.Coordinate;
import tools.Hitbox;
import javafx.scene.input.KeyCode;

import java.awt.Toolkit;
import java.util.HashMap;


public class Character {

    private Coordinate position;
    private Hitbox hitbox;
    double speedX = 0, speedY = 0;
    final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 40;
    final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 40;
    final private Image waitingSprite = new Image("resources/images/waitingCharacter.png");
    final private Image movingNorthSprite = new Image("resources/images/movingNorthCharacter.png");
    private Image activeSprite = waitingSprite;

    public Character(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position, WIDTH, HEIGHT);
    }

    public void attack() {
        Hitbox att = new Hitbox(new Coordinate(position.getX() + WIDTH / 2, position.getY() + HEIGHT + 20 ), 10, 10 );
    }

    private void validatePosition(){
    }

    public HashMap<CharacterActions, Boolean> displacement(HashMap<CharacterActions, Boolean> inputs) {
        if(inputs.get(CharacterActions.UP)) {
            position.setY(position.getY() - 40);
        }
        if(inputs.get(CharacterActions.DOWN)) {
            position.setY(position.getY() + 40);
        }
        if(inputs.get(CharacterActions.LEFT)) {
            position.setX(position.getX() - 40);
        }
        if(inputs.get(CharacterActions.RIGHT)) {
            position.setX(position.getX() + 40);
        }
        return inputs;
    }

    private void jump(){

    }

    public void displayCharacter(GraphicsContext gc) {
        gc.drawImage(activeSprite, position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
