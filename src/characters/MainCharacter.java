package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.CharacterActions;
import tools.Coordinate;
import mapping.Map;
import tools.Hitbox;

import java.lang.Math.*;
import java.awt.*;
import java.util.HashMap;

public class MainCharacter extends Characters {
	public MainCharacter(Coordinate position, Map map ) {
		super(position, map);
		imageSet.put("movingNorth", new Image("resources/images/movingNorthCharacter.png"));
		imageSet.put("movingSouth", new Image("resources/images/movingSouthCharacter.png"));
		imageSet.put("movingWest", new Image("resources/images/movingWestCharacter.png"));
		imageSet.put("movingEast", new Image("resources/images/movingEastCharacter.png"));
		imageSet.put("movingNorthEast", new Image("resources/images/movingNorthEastCharacter.png"));
		imageSet.put("movingSouthEast", new Image("resources/images/movingSouthEastCharacter.png"));
		imageSet.put("movingNorthWest", new Image("resources/images/movingNorthWestCharacter.png"));
		imageSet.put("movingSouthWest", new Image("resources/images/movingSouthWestCharacter.png"));
		imageSet.put("waiting", new Image("resources/images/waitingCharacter.png"));
		RADIUS = 2 * SIDE;
		speedLimitX = RADIUS / 3;
		speedLimitY = RADIUS / 3;
		lifeValue = 100;
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
			speedY = speedY < 0.01 ? 0 : speedY / 1.4;
		}
		if (!inputs.get(CharacterActions.RIGHT) && !inputs.get(CharacterActions.LEFT)
				|| inputs.get(CharacterActions.RIGHT) && inputs.get(CharacterActions.LEFT)) {
			speedX = speedX < 0.01 ? 0 : speedX / 1.4;
		}

	}

	public void update(HashMap<CharacterActions, Boolean> inputs, MonsterTest[] monster) {
		displacement(inputs);
		if(!collision(position)) {
			position.add(speedX / SIDE, speedY / SIDE);
		}
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public void drawHitbox(GraphicsContext gc) {
		System.out.println(this.position.getX() + ";" + this.position.getY());
		System.out.println(this.hitbox.getOrigin().toString());
		hitbox.draw(gc);
	}

	private boolean collideAttack(){
	return false;
	}
	public void displayLifeCharacter(GraphicsContext gc, @NotNull Coordinate characterPosition) {
		switch(lifeValue)
		{
			case 100:
			gc.drawImage(new Image("resources/images/longue de vie 5.png"), this.positionInt.getX()* map.getSIDE() - 30, this.positionInt.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
			break;
			case 80: gc.drawImage(new Image("resources/images/longue de vie 4.png"), this.positionInt.getX()* map.getSIDE() - 30, this.positionInt.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
			break;
			case 60 : gc.drawImage(new Image("resources/images/longue de vie 3.png"), this.positionInt.getX()* map.getSIDE() - 30, this.positionInt.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
			break;
			case 40 : gc.drawImage(new Image("resources/images/longue de vie 2.png"), this.positionInt.getX()* map.getSIDE() - 30, this.positionInt.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
			break;
			case 20 : gc.drawImage(new Image("resources/images/longue de vie 1.png"), this.positionInt.getX()* map.getSIDE() - 30, this.positionInt.getY() * map.getSIDE() - 30, 2 * RADIUS, 0.25 * RADIUS);
		}

	//void updateAttack(Coordinate originAttack , Coordinate originCharacter){
	//	if (isAttacking){
	//		matrixRotation(originCharacter,originAttack, 45/60  );
	//		isAttacking = false;
	//	}

	}




}
