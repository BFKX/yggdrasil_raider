package mapping;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import tools.Coordinate;

import java.awt.Toolkit;
import java.util.Random;

public class Map {
	private int[][] map;
	private final int lines;
	private final int columns;
	private Cave origin;
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
	final private Image ground = new Image("resources/images/ground.png");
	final private Image seCorner = new Image("resources/images/seCorner.png");
	final private Image swCorner = new Image("resources/images/swCorner.png");
	final private Image neCorner = new Image("resources/images/neCorner.png");
	final private Image nwCorner = new Image("resources/images/nwCorner.png");
	final private Image sCloseWall = new Image("resources/images/sCloseWall.png");
	final private Image eCloseWall = new Image("resources/images/eCloseWall.png");
	final private Image wCloseWall = new Image("resources/images/wCloseWall.png");
	final private Image nCloseWall = new Image("resources/images/nCloseWall.png");
	final private Image red = new Image("resources/images/red.png");
	final private Image groundVar = new Image("resources/images/groundVar.png");
	final private Image character = new Image("resources/images/waitingCharacter.png");
	final private double SIDE = HEIGHT / 60;
	private final Random pseudoRandomList;
	private final double sideMiniMap = SIDE * 0.1;
	private final double originXMiniMap;
	private final double originYMiniMap;

	public Map(int columns, int lines) {
		this.lines = lines;
		this.columns = columns;
		this.map = new int[columns][lines];
		pseudoRandomList = new Random(System.currentTimeMillis());
		// origin = new SeedRoom(columns ,lines, pseudoRandomList ,new int[] {1,1,1,0,0}
		// );
		origin = new Cave(columns, lines, pseudoRandomList);
		// origin.placeRoom(pseudoRandomList);
		current = origin;
		this.map = origin.getMap();
		addGroundVariation2(new int[] { -3, -3, -3, -3 }, 50000, 150);
		originXMiniMap = WIDTH - columns * sideMiniMap;
		originYMiniMap = HEIGHT - lines * sideMiniMap;

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

	public void createCave() {
		Random pseudoRandomList = new Random(System.currentTimeMillis());
		origin = new Cave(columns, lines, pseudoRandomList);
		current = origin;
		this.map = origin.getMap();
	}

	public void addGroundVariation1(int fillPercentage) {
		Random pseudoRandom = new Random(System.currentTimeMillis());
		Cave temp = new Cave(columns, lines, pseudoRandom);
		for (int i = 0; i < 50; i++) {
			temp.applyFiltering(temp.fullnRangefiltering(1), 4);
		}
		for (int column = 0; column < columns; column++) {
			for (int line = 0; line < lines; line++) {
				if (map[column][line] == 0) {
					if (temp.getMap()[column][line] == 1) {
						map[column][line] = 25;
					}
				}
			}
		}
	}

	private void addGroundVariation2(@NotNull int[] seeds, int radius, int limit) {
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
						if (d < radius) {
							double nb =Math.abs(pseudoRandomList.nextGaussian()) * d ;
							if (nb < limit) {
								map[i][j] = seeds[k] * (int)(nb*5/d);
							}
						}
						k++;
					}
				}
			}
		}
	}

	public void display(GraphicsContext gc, @NotNull Coordinate characterPosition) {
		int initColumn = (int) (characterPosition.getX() / SIDE) - 90;
		int initLine = (int) (characterPosition.getY() / SIDE) - 60;
		double lineOffset = (int) (characterPosition.getY() / SIDE) - HEIGHT / 72;
		double columnOffset = (int) (characterPosition.getX() / SIDE) - WIDTH / 72;
		for (int column = initColumn; column < initColumn + 180; column++) {
			for (int line = initLine; line < initLine + 120; line++) {
				if (line < 0 || line >= lines || column < 0 || column >= columns) {
					continue;
				}
				gc.drawImage(spriteSelector(map[column][line]),
						(column - columnOffset) * SIDE - characterPosition.getX() % SIDE,
						(line - lineOffset) * SIDE - characterPosition.getY() % SIDE, SIDE, SIDE);
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
				originXMiniMap + sideMiniMap * characterPosition.getX() / SIDE - sideCharacterMiniMap / 2,
				originYMiniMap + sideMiniMap * characterPosition.getY() / SIDE - sideCharacterMiniMap / 2,
				sideCharacterMiniMap, sideCharacterMiniMap);
		gc.setGlobalAlpha(1);
	}

	/**
	 * @param value value of a area on the map
	 * @return sprite of selected value
	 */
	private Image spriteSelector(int value) {
		switch (value) {
		case 0:
			return ground;
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
		case 25:
			return groundVar;
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