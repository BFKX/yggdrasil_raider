package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.Coordinate;

import java.awt.Toolkit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Map {
	private int[][] map;
	private final int lines;
	private final int columns;
	private final Cave origin;
	private Room current;
	final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
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
	private final double originXMiniMap;
	private final double originYMiniMap;
	private final int[][] mapofroom ;
	private final int raporRoomMap = 100 ;
	public Map(int columns, int lines) {
		this.lines = lines;
		this.columns = columns;
		this.mapofroom = new int [columns][lines];
		this.map = new int[columns][lines];
		pseudoRandomList = new Random(System.currentTimeMillis());
		origin = new Cave(columns, lines, pseudoRandomList);
		// origin.placeRoom(pseudoRandomList);
		current = origin;
		this.map = origin.getMap();
		addGroundVariation2(new int[] {0,-1,0}, 5000);
		originXMiniMap = WIDTH - columns * sideMiniMap;
		originYMiniMap = HEIGHT - lines * sideMiniMap;
	}

	private void placeRoom(Random pseudoRandomList , int nbroom){
		for (int i = columns/2*raporRoomMap ; i<origin.getWidth()/raporRoomMap ; i++){
			for(int j = lines/2*raporRoomMap;i<origin.getHeight()/raporRoomMap;j++){
				mapofroom[i][j]= 1 ;
			}
		}
		int [] deplacement = new int[2] ;
		Room temp = origin ;
		for ( int k = 0 ; k < nbroom ; k++){
			while (temp != null){
				int nsew =ThreadLocalRandom.current().nextInt(0,4);
				switch (nsew){
					case 1 :
						if( temp.getNorth() != null){
							temp = temp.getNorth() ;
							deplacement[1] = deplacement[1] - temp.getHeight() ; // up the origine of height
						}else {
							Coordinate temporigine = new Coordinate(columns/2*raporRoomMap + deplacement[0],
																	lines/ 2* raporRoomMap + deplacement[1]);
							int tempheight = 20 + ThreadLocalRandom.current().nextInt(-5, 5);
							int tempwidh = 20 + ThreadLocalRandom.current().nextInt(-5, 5);
							for(int i=  0; i<tempwidh ;i++  ){
								if( mapofroom[i+(int) temporigine.getX()][(int)temporigine.getY()+tempheight]==1){
									tempwidh=i-1;
									break;
								}
							}
							for( int j = 0; j < tempheight;j++  ){
								if( mapofroom[(int) temporigine.getX()+ tempwidh][(-j+(int)temporigine.getY())]==1
										||mapofroom[(int) temporigine.getX()]
										[(j+(int)temporigine.getY()- tempheight)] == 1 ){
									tempwidh=j-1;
									break;
								}
							}
							Cave temporary = new Cave(tempwidh*10,tempheight*10,pseudoRandomList);
							for (int i = (int)temporigine.getX() ; i < temporigine.getX() + + tempwidh  ; i++  ){
								for(int j = (int) temporigine.getY() - tempheight;  j < (int) temporigine.getY() ; j++){
									mapofroom[i][j] = 0;
								}
							}
						}
						break;
				}
			}
		}
	}
	private void placeRoom2(int nbroom) {
		int [][] mapofmap = new int[2*nbroom+1][2*nbroom+1];
		mapofmap[nbroom+1][nbroom+1] = 1 ;
		Room temp = origin ;
		for ( int k = 1 ; k < nbroom ;  k ++ ) {
			boolean ndispo = true , sdispo= true, estdipo =true , westdispo = true ;
			int nsew =ThreadLocalRandom.current().nextInt(0,4);
			switch (nsew) {
				case 1:
					if(ndispo){
						if (temp.getNorth()!= null){
							sdispo = false ;
							temp = temp.getNorth();
						}else{
							temp.setNorth(new Cave(200 + ThreadLocalRandom.current().nextInt(-50, 50),200 +ThreadLocalRandom.current().nextInt(-50, 50),pseudoRandomList));
						}
					}
			}

		}

	}



	private void update() {
		this.map = current.getMap();
	}

	public void createSeedRoom() {
		Random pseudoRandomList = new Random(System.currentTimeMillis());
		current = new SeedRoom(columns, lines, pseudoRandomList,
				new int[] { 1, 1, pseudoRandomList.nextInt(2), pseudoRandomList.nextInt(2) });
		update();
	}

	private void addGroundVariation2(@NotNull int[] seeds, int limit) {
		Coordinate[] seedsCoordinates = new Coordinate[seeds.length];
		for (int i = 0; i < seeds.length; i++) {
			int x = pseudoRandomList.nextInt(columns);
			int y = pseudoRandomList.nextInt(lines);
			seedsCoordinates[i] = new Coordinate(x, y);
		}
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < lines; j++) {
				if (map[i][j] == 0) {
					Coordinate ij = new Coordinate(i, j);
					int k = 0;
					for (Coordinate c : seedsCoordinates) {
						double d = c.distance(ij);
						double u1 = pseudoRandomList.nextDouble()  ;
						double u2 = pseudoRandomList.nextDouble();
						double nb = Math.sqrt((-2)*Math.log(u1))*Math.cos(u2); // gausien centrer en 0 de'ecartipe 1

						nb = Math.abs(nb)*d/900;
						if (nb < 4  ){
							map[i][j] = seeds[k] - (3-(int)(nb));
						}
						k++;
					}
				}
			}
		}
	}

	public void display(GraphicsContext gc, @NotNull Coordinate characterPosition) {
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
	}

	public void displayMiniMap(@NotNull GraphicsContext gc, Coordinate characterPosition) {
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
		if (current.getNorth() != null) {
			current = current.getNorth();
			update();
		} else {
			System.out.println("noNorth");
		}
	}

	public void moveSouth() {
		if (current.getSouth() != null) {
			current = current.getSouth();
			update();
		} else {
			System.out.println("noSouth");
		}
	}

	public void moveEast() {
		if (current.getEast() != null) {
			current = current.getEast();
			update();
		} else {
			System.out.println("noEast");
		}
	}

	public void moveWest() {
		if (current.getWest() != null) {
			current = current.getWest();
			update();
		} else {
			System.out.println("noWest");
		}
	}

	public int[][] getMap() {
		return map;
	}

	public Room getOrigin() {
		return origin;
	}
}