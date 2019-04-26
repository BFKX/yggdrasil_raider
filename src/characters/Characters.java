package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import tools.Hitbox;

import mapping.Map;

import java.awt.*;
import java.util.HashMap;

public abstract class Characters {
    Map map;
    Coordinate position;
    Hitbox hitbox;
    double speedX = 0, speedY = 0;
    double speedLimitX, speedLimitY;
    double RADIUS;
    final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/60;
    HashMap<String, Image> imageSet = new HashMap<>();
    private String activeSprite;

    Characters(Coordinate position, Map map ) {
        this.position = new Coordinate(0, 0);
        this.hitbox = new Hitbox(position, RADIUS);
        this.map = map ;
    }

    private Image spriteSelector() {
        if (speedY > 1 && speedX > 1) {
            activeSprite = "movingSouthEast";
        } else if (speedY > 1 && Math.abs(speedX) < 1) {
            activeSprite = "movingSouth";
        } else if (speedY > 1 && speedX < -1) {
            activeSprite = "movingSouthWest";
        } else if (speedY < -1 && speedX > 1) {
            activeSprite = "movingNorthEast";
        } else if (Math.abs(speedY) < 1 && speedX > 1) {
            activeSprite = "movingEast";
        } else if (speedY < -1 && Math.abs(speedX) < 1) {
            activeSprite = "movingNorth";
        } else if (speedY < -1 && speedX < -1) {
            activeSprite = "movingNorthWest";
        } else if (Math.abs(speedY) < 1 && speedX < -1) {
            activeSprite = "movingWest";
        }
        return imageSet.get(activeSprite);
    }

    public void display(GraphicsContext gc) {
        gc.drawImage(spriteSelector(), Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - RADIUS / 2,
                Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - RADIUS / 2, RADIUS, RADIUS);
    }
}
