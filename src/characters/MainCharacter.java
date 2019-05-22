package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.CharacterActions;
import tools.Coordinate;
import mapping.Map;
import tools.Hitbox;
import tools.Node;

import java.util.HashMap;

public class MainCharacter extends Character {
	public MainCharacter(Coordinate position, Map map ) {
		super(position);
		this.map=map;
		spriteSet.put("movingNorth", new Image("resources/images/movingNorthCharacter.png"));
		spriteSet.put("movingSouth", new Image("resources/images/movingSouthCharacter.png"));
		spriteSet.put("movingWest", new Image("resources/images/movingWestCharacter.png"));
		spriteSet.put("movingEast", new Image("resources/images/movingEastCharacter.png"));
		spriteSet.put("movingNorthEast", new Image("resources/images/movingNorthEastCharacter.png"));
		spriteSet.put("movingSouthEast", new Image("resources/images/movingSouthEastCharacter.png"));
		spriteSet.put("movingNorthWest", new Image("resources/images/movingNorthWestCharacter.png"));
		spriteSet.put("movingSouthWest", new Image("resources/images/movingSouthWestCharacter.png"));
		spriteSet.put("waiting", new Image("resources/images/waitingCharacter.png"));
		lifeBar.put(100, new Image("images/lifebar5.png"));
		lifeBar.put(80, new Image("images/lifebar4.png"));
		lifeBar.put(60, new Image("images/lifebar3.png"));
		lifeBar.put(40, new Image("images/lifebar2.png"));
		lifeBar.put(20, new Image("images/lifebar1.png"));
		RADIUS = 2 * SIDE;
		hitbox.setRadius(RADIUS);
		speedLimitX = RADIUS / 3;
		speedLimitY = RADIUS / 3;
		healthPoint = 100;
		this.type = 0;
	}

	private void displacement(HashMap<CharacterActions, Boolean> inputs) {
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

	public void update(HashMap<CharacterActions, Boolean> inputs) {

		displacement(inputs);
		if(!collision(position,map.getCurrent().getMap())) {
			position.add(speedX / SIDE, speedY / SIDE);
		}
		if(position.getX() < 0 ){
			map.moveEast();
			position=new Coordinate(map.getMap().length-1,position.getY());
		}else if(position.getX()>map.getMap().length -1){
			map.moveWest();
			position= new Coordinate(0, position.getY());
		}else if(position.getY() <0){
			map.moveNorth();
			position = new Coordinate(position.getX(),map.getMap()[0].length-1);
		}else if(position.getY() > map.getMap()[0].length -1 ){
			map.moveSouth();
			position = new Coordinate(position.getX(),0);
		}
	}

	public Coordinate getPosition() {
		return position;
	}

	private boolean collideAttack(){
	return false;
	}
	public void displayLifeCharacter(GraphicsContext gc, Coordinate characterPosition) {

	//void updateAttack(Coordinate originAttack , Coordinate originCharacter){
	//	if (isAttacking){
	//		matrixRotation(originCharacter,originAttack, 45/60  );
	//		isAttacking = false;
	//	}

	}

	public void attack(){
		System.out.println("Main :"+position+"Radius" + this.getRADIUS()+" ; SIDE : " + SIDE);
		Hitbox hitboxAttack = new Hitbox(getPosition(), RADIUS / SIDE);
		map.getCurrent().getMonsters().isHit(hitboxAttack);
	}

}
