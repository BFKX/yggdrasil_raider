package monster;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mapping.Map;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.*;

public abstract class Monster {
    protected Coordinate position;
    protected final Hitbox hitbox;
    protected double speedX , speedY ;
    protected int directionX ;
    protected  int directionY ;
    protected double speedLimitX ;
    protected double speedLimitY ;
    private double RADIUS = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
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

    public Monster(Coordinate coordinate){
        this.position = coordinate ;
        this.hitbox = new Hitbox(position, RADIUS);
    }

    public void display(GraphicsContext gc) {
        if (speedY > 1 && speedX > 1) {
            activeSprite = movingSouthEastSprite;
        } else if (speedY > 1 && Math.abs(speedX) < 1) {
            activeSprite = movingSouthSprite;
        } else if (speedY > 1 && speedX < -1) {
            activeSprite = movingSouthWestSprite;
        } else if (speedY < -1 && speedX > 1) {
            activeSprite = movingNorthEastSprite;
        } else if (Math.abs(speedY) < 1 && speedX > 1) {
            activeSprite = movingEastSprite;
        } else if (speedY < -1 && Math.abs(speedX) < 1) {
            activeSprite = movingNorthSprite;
        } else if (speedY < -1 && speedX < -1) {
            activeSprite = movingNorthWestSprite;
        } else if (Math.abs(speedY) < 1 && speedX < -1) {
            activeSprite = movingWestSprite;
        } else if (Math.abs(speedY) < 1 && Math.abs(speedX) < 1) {
            activeSprite = waitingCharacter;
        }
        gc.drawImage(activeSprite, Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - RADIUS / 2,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - RADIUS / 2, RADIUS, RADIUS);
    }

     public abstract void updateDeplacement(Map map);

    protected void update(){
        this.position.add(  speedX ,  speedY);
    }

    public Coordinate getPosition() {
        return position;
    }
}
