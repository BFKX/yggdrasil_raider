package mapping;

import characters.MainCharacter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;

import java.awt.Toolkit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
	private int[][] map;
	private final Cave origin;
	private Room current;
	private int lines;
	private int columns;
	final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	final private Image voidImage = new Image("resources/images/void.png");
	final private Image sWall = new Image("resources/images/sWall.png");
	final private Image eWall = new Image("resources/images/eWall.png");
	final private Image nWall = new Image("resources/images/nWall.png");
	final private Image wWall = new Image("resources/images/wWall.png");
	final private Image seWall = new Image("resources/images/seWall.png");
	final private Image neWall = new Image("resources/images/neWall.png");
	final private Image swWall = new Image("resources/images/swWall.png");
	final private Image nwWall = new Image("resources/images/nwWall.png");
	final private Image seCorner = new Image("resources/images/seCorner.png");
	final private Image swCorner = new Image("resources/images/swCorner.png");
	final private Image neCorner = new Image("resources/images/neCorner.png");
	final private Image nwCorner = new Image("resources/images/nwCorner.png");
	final private Image sCloseWall = new Image("resources/images/sCloseWall.png");
	final private Image eCloseWall = new Image("resources/images/eCloseWall.png");
	final private Image wCloseWall = new Image("resources/images/wCloseWall.png");
	final private Image nCloseWall = new Image("resources/images/nCloseWall.png");
	final private Image red = new Image("resources/images/red.png");
	final private Image groundVar1 = new Image("resources/images/groundVar1.png");
	final private Image groundVar2 = new Image("resources/images/groundVar2.png");
	final private Image groundVar3 = new Image("resources/images/groundVar3.png");
	final private Image groundVar4 = new Image("resources/images/groundVar4.png");
	final private Image groundVar5 = new Image("resources/images/groundVar5.png");
	final private Image character = new Image("resources/images/waitingCharacter.png");
	final private double SIDE = HEIGHT / 60;
	private final Random pseudoRandomList;
	private final double sideMiniMap = SIDE * 0.1;
	private double originXMiniMap;
	private double originYMiniMap;
	Room [] [] mapOfRoom;
	MainCharacter mainCharacter;

	public Map(int n,MainCharacter mainCharacter) {
		System.out.println(" 3 "+mainCharacter);
		pseudoRandomList = new Random(System.currentTimeMillis());
		mapOfRoom = new Room [2*n+1] [2*n+1];
		this.mainCharacter = mainCharacter;
		System.out.println(" 4 "+mainCharacter);
		origin = new Cave(ThreadLocalRandom.current().nextInt(100, 110),
				ThreadLocalRandom.current().nextInt(100, 110), pseudoRandomList,new Coordinate(n, n));
		origin.createMonsters(mainCharacter);
		System.out.println(" 5 "+mainCharacter);
		current = origin;
		update();
		mapOfRoom[n][n] = origin ;
		update();
		placeRoom(n);
		placeWall();
	}


	public void placeWall(){
		for (int i = 0; i < mapOfRoom.length; i++){
			for(int j = 0 ; j<mapOfRoom[0].length ; j++){
				if(mapOfRoom[i][j] != null){
					mapOfRoom[i][j].placeWall();
					mapOfRoom[i][j].addGroundVariation(new int[] {0,-1,0}, 5000);
				}
			}
		}

	}
	private void placeRoom (int n ) { // cree une sale
		for ( int k = 0 ; k<n ; k++  ){
			int tempWidth = ThreadLocalRandom.current().nextInt(100, 110);
			int tempHeight = ThreadLocalRandom.current().nextInt(100, 110);
			Cave temp = new Cave(tempWidth,tempHeight,pseudoRandomList,
					positionOnMap(mapOfRoom,origin.getPosition().copy()));
			temp.createMonsters(mainCharacter);
			placeRoom(temp);
		}
	}
	private Coordinate positionOnMap(Room[][]MapofRoom, Coordinate coordinate){//la position de la room dans map
		int val = ThreadLocalRandom.current().nextInt(0, 4);
		switch (val) {
			case 0 :
				coordinate.add(0, -1); //nord
			case 1 :
				coordinate.add(1, 0); //est
			case 2 :
				coordinate.add(0, 1); // sud
			case 3:
				coordinate.add(-1, 0); //west
		}
		if (mapOfRoom[(int) coordinate.getX()][(int) coordinate.getY()] != null) {
			return positionOnMap(mapOfRoom, coordinate);
		} else {
			System.out.println(coordinate.toString());
			return coordinate;
		}
	}
	private void placeRoom(Room current){
		int currentPositionX = (int) current.getPosition().getX();
		int currentPositionY = (int) current.getPosition().getY();
		mapOfRoom[currentPositionX][currentPositionY] = current ;
		if(mapOfRoom[currentPositionX][currentPositionY+1] != null){ //créé le lien avec le sud
			mapOfRoom[currentPositionX][currentPositionY+1].setNorth(current);
			current.setSouth(mapOfRoom[currentPositionX][currentPositionY+1]);
			int index = verticalIndiceLink(current,mapOfRoom[currentPositionX][currentPositionY+1]);
			current.southVoid(index);
			mapOfRoom[currentPositionX][currentPositionY+1].northVoid(index);
		}
		if(mapOfRoom[currentPositionX][currentPositionY-1] != null){ // cree le lien avec le nord
			mapOfRoom[currentPositionX][currentPositionY-1].setSouth(current);
			current.setNorth(mapOfRoom[currentPositionX][currentPositionY-1]);
			int indic = verticalIndiceLink(current,mapOfRoom[currentPositionX][currentPositionY-1]);
			current.northVoid(indic);
			mapOfRoom[currentPositionX][currentPositionY-1].southVoid(indic);
		}
		if(mapOfRoom[currentPositionX+1][currentPositionY] != null){ // cree le lien a l'est
			mapOfRoom[currentPositionX+1][currentPositionY].setWest(current);
			current.setEast(mapOfRoom[currentPositionX+1][currentPositionY]);
			int indic = horisontalIndiceLink(current,mapOfRoom[currentPositionX+1][currentPositionY]);
			current.eastVoid(indic);
			mapOfRoom[currentPositionX+1][currentPositionY].westVoid(indic);
		}
		if(mapOfRoom[currentPositionX-1][currentPositionY] != null){ //cree le lien a l'ouest
			mapOfRoom[currentPositionX-1][currentPositionY].setEast(current);
			current.setWest(mapOfRoom[currentPositionX-1][currentPositionY]);
			int indic = horisontalIndiceLink(current,mapOfRoom[currentPositionX-1][currentPositionY]);
			current.westVoid(indic);
			mapOfRoom[currentPositionX-1][currentPositionY].eastVoid(indic);
		}
	}

	private int horisontalIndiceLink(Room room1, Room room2){
		int min = room1.height < room2.height ? room1.height : room2.height ;
		return ThreadLocalRandom.current().nextInt( min / 3, min * 2 /3 );
	}

	private int verticalIndiceLink(Room room1, Room room2){
		int min = room1.width < room2.width ? room1.width : room2.width ;
		return ThreadLocalRandom.current().nextInt( min / 3, min * 2 /3 );
	}

	private void update() {
		this.map = current.getMap();
		this.lines = map[0].length;
		this.columns = map.length;
		originXMiniMap = WIDTH - columns * sideMiniMap;
		originYMiniMap = HEIGHT - lines * sideMiniMap;
	}
	public void updateMonster(GraphicsContext gc){
		current.getMonsters().update(gc);
	}
	public void display(GraphicsContext gc, Coordinate characterPosition) {
		double positionX = characterPosition.getX();
		double positionY = characterPosition.getY();
		int initColumn = (int) positionX - 107;
		int initLine = (int) positionY - 60;
		double lineOffset = positionY - HEIGHT / (2 * SIDE);
		double columnOffset = positionX - WIDTH / (2 * SIDE);
		for (int column = initColumn; column < initColumn + 214; column++) {
			for (int line = initLine; line < initLine + 120; line++) {
				if (line < 0 || line >= lines || column < 0 || column >= columns) {
					continue;
				}
				gc.drawImage(spriteSelector(map[column][line]),
						(column - columnOffset) * SIDE ,
						(line - lineOffset) * SIDE , SIDE, SIDE);
			}
		}
		current.getMonsters().display(gc, mainCharacter);
	}

	public Room getCurrent() {
		return current;
	}

	public void displayMiniMap(GraphicsContext gc, Coordinate characterPosition) {
		gc.setGlobalAlpha(0.7);
		Image sprite;
		double sideCharacterMiniMap = SIDE / 3;
		for (int column = 0; column < columns; column++) {
			for (int line = 0; line < lines; line++) {
				sprite = spriteSelector(map[column][line]);
				if (!sprite.equals(voidImage)) {
					gc.drawImage(sprite, originXMiniMap + column * sideMiniMap, originYMiniMap + line * sideMiniMap,
							sideMiniMap, sideMiniMap);
				}
			}
		}
		gc.drawImage(character,
				originXMiniMap + sideMiniMap * characterPosition.getX()  - sideCharacterMiniMap / 2,
				originYMiniMap + sideMiniMap * characterPosition.getY()  - sideCharacterMiniMap / 2,
				sideCharacterMiniMap, sideCharacterMiniMap);
		gc.setGlobalAlpha(1);
	}

	public void displayMapOfMap(){
		for( int i = 0 ;  i < mapOfRoom.length ; i++){
			for( int j =0 ; j < mapOfRoom[0].length ; j++) {
				if ( mapOfRoom[j][i] == null ) {
						System.out.print(0);}
				else {
					System.out.print(1);
					//.out.println(" "+j+";"+i);
				}
			}
			System.out.println();
		}
	}
	/**
	 * @param value value of a area on the map
	 * @return sprite of selected value
	 */
	private Image spriteSelector(int value) {
		switch (value) {
		case -4:
			return groundVar5;
		case -3:
			return groundVar4;
		case -2:
			return groundVar3;
		case -1:
			return groundVar2;
		case 0:
			return groundVar1;
		case 1:
			return voidImage;
		case 2:
			return nWall;
		case 3:
			return wWall;
		case 4:
			return nwWall;
		case 6:
			return sWall;
		case 8:
			return swWall;
		case 9:
			return wCloseWall;
		case 12:
			return eWall;
		case 13:
			return neWall;
		case 15:
			return nCloseWall;
		case 17:
			return seWall;
		case 18:
			return eCloseWall;
		case 19:
			return sCloseWall;
		case 21:
			return nwCorner;
		case 41:
			return swCorner;
		case 82:
			return seCorner;
		case 163:
			return neCorner;
		default:
			 return red;
		}
	}

	public void moveNorth() {
		Coordinate position = current.getPosition();
				current = mapOfRoom[(int) position.getX()][(int) position.getY()-1];
		System.out.println("goN");
		update();
	}

	public void moveSouth() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()][(int) position.getY()+1];
		System.out.println("goN");
		update();
	}

	public void moveEast() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()-1][(int) position.getY()];
		System.out.println("goN");
		update();
	}

	public void moveWest() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()+1][(int) position.getY()];
		System.out.println("goN");
		update();
	}

	public int[][] getMap() {
		return map;
	}

	public Room getOrigin() {
		return origin;
	}

	public double getWIDTH() {
		return WIDTH;
	}

	public double getHEIGHT() {
		return HEIGHT;
	}

	public double getSIDE() {
		return SIDE;
	}
}
