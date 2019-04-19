package character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.CharacterActions;
import tools.Coordinate;
import tools.Hitbox;
import mapping.Map;

import java.awt.Toolkit;
import java.util.HashMap;

public class Character {
	private Coordinate position;
	private final Hitbox hitbox;
	private double speedX = 0, speedY = 0;
	final private double RADIUS = 2 * Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
	final private double speedLimitX = RADIUS * 15;
	final private double speedLimitY = RADIUS * 15;
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

	public Character(Coordinate position) {
		this.position = new Coordinate(0, 0);
		this.hitbox = new Hitbox(position, RADIUS);
	}

	private void displacement(@NotNull HashMap<CharacterActions, Boolean> inputs) {
		if (!(inputs.get(CharacterActions.UP) && inputs.get(CharacterActions.DOWN)
				|| inputs.get(CharacterActions.LEFT) && inputs.get(CharacterActions.RIGHT))) {
			if (inputs.get(CharacterActions.UP) && speedY > -speedLimitY) {
				speedY -= speedLimitY / 13;
			}
			if (inputs.get(CharacterActions.DOWN) && speedY < speedLimitY) {
				speedY += speedLimitY / 13;
			}
			if (inputs.get(CharacterActions.LEFT) && speedX > -speedLimitX) {
				speedX -= speedLimitX / 13;
			}
			if (inputs.get(CharacterActions.RIGHT) && speedX < speedLimitX) {
				speedX += speedLimitX / 13;
			}
		}
		if (!inputs.get(CharacterActions.UP) && !inputs.get(CharacterActions.DOWN)
				|| inputs.get(CharacterActions.UP) && inputs.get(CharacterActions.DOWN)) {
			speedY /= 1.4;
		}
		if (!inputs.get(CharacterActions.RIGHT) && !inputs.get(CharacterActions.LEFT)
				|| inputs.get(CharacterActions.RIGHT) && inputs.get(CharacterActions.LEFT)) {
			speedX /= 1.4;
		}
	}

	public void update(HashMap<CharacterActions, Boolean> inputs) {
		displacement(inputs);
		position.add(speedX / RADIUS, speedY / RADIUS);

	}

	public void displayCharacter(GraphicsContext gc) {
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
