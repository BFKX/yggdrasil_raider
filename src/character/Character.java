package character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.CharacterActions;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.Toolkit;
import java.util.HashMap;


public class Character {

    private Coordinate position;
    private Hitbox hitbox;
    private double speedX = 0, speedY = 0;
    final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 40;
    final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 40;
    final private double speedLimitX = WIDTH / 5;
    final private double speedLimitY = HEIGHT / 5;
    final private Image waitingCharacter = new Image("resources/images/waitingCharacter.png");
    final private Image movingNorthSprite = new Image("resources/images/movingNorthCharacter.png");
    final private Image movingSouthSprite = new Image("resources/images/movingSouthCharacter.png");
    final private Image movingWestSprite = new Image("resources/images/movingWestCharacter.png");
    final private Image movingEastSprite = new Image("resources/images/movingEastCharacter.png");
    final private Image movingNorthEastSprite = new Image("resources/images/movingNorthEastCharacter.png");
    final private Image movingSouthEastSprite = new Image("resources/images/movingSouthEastCharacter.png");
    final private Image movingNorthWestSprite = new Image("resources/images/movingNorthWestCharacter.png");
    final private Image movingSouthWestSprite = new Image("resources/images/movingSouthWestCharacter.png");
    private Image activeSprite = waitingCharacter;

    public Character(Coordinate position, Hitbox hitbox) {
        this.position = position;
        this.hitbox = new Hitbox(position, WIDTH, HEIGHT);
    }

    public void attack(GraphicsContext gc) {
        Hitbox att = new Hitbox(new Coordinate(position.getX() + WIDTH, position.getY() + HEIGHT + 20 ), 10, 10 );
    }

    private void validatePosition(){
    }

    public void displacement(HashMap<CharacterActions, Boolean> inputs) {
        if(!(inputs.get(CharacterActions.UP) && inputs.get(CharacterActions.DOWN) || inputs.get(CharacterActions.LEFT) && inputs.get(CharacterActions.RIGHT))) {
            if(inputs.get(CharacterActions.UP) && speedY > -speedLimitY) {
                speedY -= speedLimitY / 10;
            }
            if(inputs.get(CharacterActions.DOWN) && speedY < speedLimitY) {
                speedY += speedLimitY / 10;
            }
            if(inputs.get(CharacterActions.LEFT) && speedX > -speedLimitX) {
                speedX -= speedLimitX / 10;
            }
            if(inputs.get(CharacterActions.RIGHT) && speedX < speedLimitX) {
                speedX += speedLimitX / 10;
            }
        }
        if(!inputs.get(CharacterActions.UP) && !inputs.get(CharacterActions.DOWN) || inputs.get(CharacterActions.UP) && inputs.get(CharacterActions.DOWN)) {
            speedY /= 2;
        }
        if(!inputs.get(CharacterActions.RIGHT) && !inputs.get(CharacterActions.LEFT) || inputs.get(CharacterActions.RIGHT) && inputs.get(CharacterActions.LEFT)) {
            speedX /= 2;
        }
    }

    public void update() {
        position.add(speedX, speedY);
    }

    public void displayCharacter(GraphicsContext gc) {
        if(speedY > 1 && speedX > 1) {
            activeSprite = movingSouthEastSprite;
        } else if(speedY > 1 && Math.abs(speedX) < 1) {
            activeSprite = movingSouthSprite;
        } else if(speedY > 1 && speedX < -1) {
            activeSprite = movingSouthWestSprite;
        } else if(speedY < -1 && speedX > 1) {
            activeSprite = movingNorthEastSprite;
        } else if(Math.abs(speedY) < 1 && speedX > 1) {
            activeSprite = movingEastSprite;
        } else if(speedY < -1 && Math.abs(speedX) < 1) {
            activeSprite = movingNorthSprite;
        } else if(speedY < -1 && speedX < -1) {
            activeSprite = movingNorthWestSprite;
        } else if(Math.abs(speedY) < 1 && speedX < -1) {
            activeSprite = movingWestSprite;
        } else if(Math.abs(speedY) < 1 && Math.abs(speedX) < 1) {
            activeSprite = waitingCharacter;
        }

        gc.drawImage(activeSprite, position.getX(), position.getY(), WIDTH, HEIGHT);
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void drawHitbox(GraphicsContext gc) {
        hitbox.draw(gc);
    }
}
