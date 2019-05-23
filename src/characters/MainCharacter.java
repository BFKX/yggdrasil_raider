package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.CharacterActions;
import tools.Coordinate;
import mapping.Map;
import tools.Hitbox;
import tools.ImageSet;

import java.util.HashMap;

public class MainCharacter extends Character {
	final Image fullHeart = new Image("resources/images/heartFull.png");
	final Image emptyHeart = new Image("resources/images/heartEmpty.png");
	final Image halfHeart = new Image("resources/images/heartHalf.png");
	final double staminaMax = 100;
	double stamina ;
	private boolean isRuning = false;
	double baseSpeedLimiteX;
	double baseSpeedLimiteY;
	public MainCharacter(Coordinate position, Map map ) {
		super(position);
		this.map=map;
		HashMap<Integer, Image> images = new HashMap<>();
		images.put(0, new Image("resources/images/noFootCharacter.png"));
		images.put(1, new Image("resources/images/leftFootCharacter1.png"));
		images.put(2, new Image("resources/images/leftFootCharacter2.png"));
		images.put(3, new Image("resources/images/leftFootCharacter3.png"));
		images.put(4, new Image("resources/images/leftFootCharacter4.png"));
		images.put(5, new Image("resources/images/leftFootCharacter5.png"));
		images.put(11, new Image("resources/images/rightFootCharacter1.png"));
		images.put(12, new Image("resources/images/rightFootCharacter2.png"));
		images.put(13, new Image("resources/images/rightFootCharacter3.png"));
		images.put(14, new Image("resources/images/rightFootCharacter4.png"));
		images.put(15, new Image("resources/images/rightFootCharacter5.png"));
		int[] sequence = {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 11, 12, 13, 14, 15, 14, 13, 12, 11};
		sprites = new ImageSet(images, sequence);
		waiting = new Image("resources/images/noFootCharacter.png");
		RADIUS = 3 * SIDE;
		hitbox.setRadius(RADIUS);
		baseSpeedLimiteX = SIDE / 3;
		baseSpeedLimiteY = SIDE / 3;
		speedLimitX =baseSpeedLimiteY;
		speedLimitY =baseSpeedLimiteY;
		runingSpeedLimitX=speedLimitX*2;
		runingSpeedLimitY=speedLimitY*2;
		stamina = 100;
		healthPoint = 100;
		this.type = 0;
	}
	public void startRun(){
		isRuning= true ;
	}

	public void stopRun(){
		isRuning=false;
	}

	public void dash(){
		if(stamina > 25 ) {
			this.speedX =  signe(speedX) * baseSpeedLimiteX * 10;
			this.speedY = signe(speedY) * baseSpeedLimiteY  * 10;
			stamina= stamina - 20 ;
		}
	}
	private int signe (double x ){
		if( x > 0 ) { return  1;}
		if(x<0 ) { return -1;}
		return 0 ;
	}
	private void displacement(HashMap<CharacterActions, Boolean> inputs) {
		if(isRuning && stamina > 0){
			speedLimitX= runingSpeedLimitX;
			speedLimitY= runingSpeedLimitY;
			stamina -= 40.0/60;
			System.out.println("stamina : "+ stamina );
		}else{
			speedLimitX = baseSpeedLimiteX;
			speedLimitY = baseSpeedLimiteY;
			if (stamina < staminaMax && ! isRuning ){
			 	stamina = stamina+ 15.0/60;
				System.out.println("Stamina: " + stamina);
			}
		}
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
		if( Math.abs(speedX) > speedLimitX){
			speedX = speedX/1.4;
		}
		if( Math.abs(speedY) > speedLimitY){
			speedY = speedY/ 1.4;
		}
	}

	public void update(HashMap<CharacterActions, Boolean> inputs, MonsterSet monsterSet) {
		displacement(inputs);
		System.out.println("MAIn: " + position + this.hitbox.);
		for(int k = 0 ; k < monsterSet.hit(hitbox); k++){
			healthPoint -= 10;
		}
		if(!collision(position,map.getCurrent().getMap())) {
			position.add(speedX / SIDE, speedY / SIDE);
		}
		if(position.getX() < 0 ){
			map.moveEast();
			position=new Coordinate(map.getMap().length - 1,position.getY());
		}else if(position.getX()>map.getMap().length - 1){
			map.moveWest();
			position= new Coordinate(0, position.getY());
		}else if(position.getY() < 0){
			map.moveNorth();
			position = new Coordinate(position.getX(), map.getMap()[0].length-1);
		}else if(position.getY() > map.getMap()[0].length - 1 ){
			map.moveSouth();
			position = new Coordinate(position.getX(), 0);
		}
		this.hitbox.setOrigin(this.position);
		map.getCurrent().getMonsters().setMainCharacterPosition(position);
	}

	public Coordinate getPosition() {
		return position;
	}

	public void attack(GraphicsContext gc){
		System.out.println("Main :"+position+"Radius" + this.getRADIUS()+" ; SIDE : " + SIDE);
		Hitbox hitboxAttack = new Hitbox(new Coordinate(position.getX() + signe(speedX)*RADIUS/SIDE/1.2,
				position.getY() + signe(speedY)*RADIUS/SIDE/1.2)
				, RADIUS / SIDE );
		hitboxAttack.draw(gc,this.getPosition());
		map.getCurrent().getMonsters().isHit(hitboxAttack);
	}

	public void displayHealth(GraphicsContext gc) {
		switch (healthPoint) {
			case 100:
				for (int i = 1; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 90:
				for (int i = 2; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(halfHeart, WIDTH - RADIUS, 0, RADIUS, RADIUS);
				break;
			case 80:
				for (int i = 2; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(emptyHeart, WIDTH - RADIUS, 0, RADIUS, RADIUS);
				break;
			case 70:
				for (int i = 3; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(halfHeart, WIDTH - 2 * RADIUS, 0, RADIUS, RADIUS);
				gc.drawImage(emptyHeart, WIDTH - RADIUS, 0, RADIUS, RADIUS);
				break;
			case 60:
				for (int i = 3; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				for (int i = 1; i < 3; i++) {
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 50:
				for (int i = 4; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(halfHeart, WIDTH - 3 * RADIUS, 0, RADIUS, RADIUS);
				for (int i = 1; i < 3; i++) {
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 40:
				for (int i = 4; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				for (int i = 1; i < 4; i++) {
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 30:
				for (int i = 5; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(halfHeart, WIDTH - 4 * RADIUS, 0, RADIUS, RADIUS);
				for (int i = 1; i < 5; i++) {
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 20:
				for (int i = 5; i <= 5; i++){
					gc.drawImage(fullHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				for (int i = 1; i < 5; i++) {
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
			case 10:
				for (int i = 1; i < 5; i++){
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				gc.drawImage(halfHeart, WIDTH - RADIUS, 0, RADIUS, RADIUS);
				break;
			case 0:
				for (int i = 1; i <= 5; i++){
					gc.drawImage(emptyHeart, WIDTH - i * RADIUS, 0, RADIUS, RADIUS);
				}
				break;
		}
	}
}
