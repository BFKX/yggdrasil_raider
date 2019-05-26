package mapping;

import characters.MainCharacter;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
	private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
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
	final private Image nCorners = new Image("resources/images/nCorners.png");
	final private Image wCorners = new Image("resources/images/wCorners.png");
	final private Image sCorners = new Image("resources/images/sCorners.png");
	final private Image eCorners = new Image("resources/images/eCorners.png");
	final private Image sCloseWall = new Image("resources/images/sCloseWall.png");
	final private Image eCloseWall = new Image("resources/images/eCloseWall.png");
	final private Image wCloseWall = new Image("resources/images/wCloseWall.png");
	final private Image nCloseWall = new Image("resources/images/nCloseWall.png");
	final private Image red = new Image("resources/images/red.png");
	final private Image groundVar0 = new Image("resources/images/groundVar0.png");
	final private Image groundVar1 = new Image("resources/images/groundVar1.png");
	final private Image groundVar2 = new Image("resources/images/groundVar2.png");
	final private Image groundVar3 = new Image("resources/images/groundVar3.png");
	final private Image groundVar4 = new Image("resources/images/groundVar4.png");
	final private Image groundVar5 = new Image("resources/images/groundVar5.png");
	final private double SIDE = HEIGHT / 60;
	private final Random pseudoRandomList;
	private final double sideMiniMap = SIDE * 0.1;
	private double originXMiniMap;
	private double originYMiniMap;
	private Room[][] mapOfRoom;
	private MainCharacter mainCharacter;
	private final int roomScale = 150;

	public Map(int n,MainCharacter mainCharacter) {

		pseudoRandomList = new Random(System.currentTimeMillis());
		mapOfRoom = new Room [2*n+1] [2*n+1];
		this.mainCharacter = mainCharacter;

		origin = new Cave(ThreadLocalRandom.current().nextInt(roomScale, roomScale * 11 / 10),
				ThreadLocalRandom.current().nextInt(roomScale, roomScale * 11 / 10), pseudoRandomList,new Coordinate(n, n));
		origin.createMonsters(mainCharacter);

		current = origin;
		update();
		mapOfRoom[n][n] = origin ;
		update();
		placeRoom(n);
		placeWall();
	}


	private void placeWall(){
		for (int i = 0; i < mapOfRoom.length; i++){
			for(int j = 0 ; j<mapOfRoom[0].length ; j++){
				if(mapOfRoom[i][j] != null){
					for ( int k = 0 ; i <6 ; i++){
						mapOfRoom[i][j].delete25(1);
					}
					mapOfRoom[i][j].placeWall();
					mapOfRoom[i][j].addGroundVariation(new int[] {0,-1,0}, 5000);
				}
			}
		}

	}
	private void placeRoom (int n ) { // cree une sale
		for ( int k = 0 ; k<n ; k++  ){
			int tempWidth = ThreadLocalRandom.current().nextInt(roomScale, roomScale * 11 / 10);
			int tempHeight = ThreadLocalRandom.current().nextInt(roomScale, roomScale * 11 / 10);
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
			int indic = horizontalIndiceLink(current,mapOfRoom[currentPositionX+1][currentPositionY]);
			current.eastVoid(indic);
			mapOfRoom[currentPositionX+1][currentPositionY].westVoid(indic);
		}
		if(mapOfRoom[currentPositionX-1][currentPositionY] != null){ //cree le lien a l'ouest
			mapOfRoom[currentPositionX-1][currentPositionY].setEast(current);
			current.setWest(mapOfRoom[currentPositionX-1][currentPositionY]);
			int indic = horizontalIndiceLink(current,mapOfRoom[currentPositionX-1][currentPositionY]);
			current.westVoid(indic);
			mapOfRoom[currentPositionX-1][currentPositionY].eastVoid(indic);
		}
	}

	private int horizontalIndiceLink(Room room1, Room room2){
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

	public void updateMonster(){
		current.getMonsters().update(mainCharacter);
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
		gc.save();
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
		gc.setFill(Color.DARKORANGE);
		gc.fillRect(originXMiniMap + sideMiniMap * characterPosition.getX()  - sideCharacterMiniMap / 2,
				originYMiniMap + sideMiniMap * characterPosition.getY()  - sideCharacterMiniMap / 2,
				sideCharacterMiniMap, sideCharacterMiniMap);
		gc.restore();
	}

	public void fullScreenMap(GraphicsContext gc, Coordinate characterPosition) {
		int localLines = current.getHeight() / 2;
		int localColumns = current.getWidth() / 2;
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		double localSide = HEIGHT / current.getHeight();
		for (int column = 0; column < columns; column++) {
			for (int line = 0; line < lines; line++) {
				gc.drawImage(spriteSelector(map[column][line]), (column - localColumns) * localSide + WIDTH / 2, (line - localLines) * localSide + HEIGHT / 2, localSide, localSide);
			}
		}
		gc.save();
		gc.setFill(Color.DARKORANGE);
		gc.fillRect((characterPosition.getX() - localColumns) * localSide + WIDTH / 2, (characterPosition.getY() - localLines) * localSide + HEIGHT / 2, localSide, localSide);
		gc.restore();
	}

	private Image spriteSelector(int value) {
		switch (value) {
		case -5:
			return groundVar5;
		case -4:
			return groundVar4;
		case -3:
			return groundVar3;
		case -2:
			return groundVar2;
		case -1:
			return groundVar1;
		case 0:
			return groundVar0;
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
		case 244:
			return eCorners;
		case 82:
			return seCorner;
		case 183:
			return nCorners;
		case 163:
			return neCorner;
		case 122:
			return sCorners;
		case 61:
			return wCorners;
		default:
			 return voidImage;
		}
	}

	public void moveNorth() {
		Coordinate position = current.getPosition();
				current = mapOfRoom[(int) position.getX()][(int) position.getY()-1];
		update();
	}

	public void moveSouth() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()][(int) position.getY()+1];
		update();
	}

	public void moveEast() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()-1][(int) position.getY()];
		update();
	}

	public void moveWest() {
		Coordinate position = current.getPosition();
		current = mapOfRoom[(int) position.getX()+1][(int) position.getY()];
		update();
	}

	public int[][] getMap() {
		return map;
	}

	public double getSIDE() {
		return SIDE;
	}
}
