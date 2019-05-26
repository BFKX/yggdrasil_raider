package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapping.Map;
import tools.*;
import characters.MainCharacter;

import java.awt.Toolkit;
import java.util.HashMap;

class GameController extends Application {
	private boolean mapShown = false;
	final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private boolean gameover = false;
	private MainCharacter mainCharacter;
	final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private Canvas canvas = new Canvas(WIDTH, HEIGHT);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Scene scene;
	private final Stage primaryStage;
	private boolean pause = false;
	private boolean pauseShown = false;
	private final Button resumeButton = new Button();
	private final Button quitButton = new Button();
	private final Button muteButton = new Button();
	private final Text text = new Text("Pause");
	private final HashMap<CharacterActions, Boolean> inputs = new HashMap<>();
	private final MusicPlayer music = new MusicPlayer("/resources/audio/inGame.mp3", HEIGHT / 15);
	private Map map;
	final private Image pauseBackground = new Image("resources/images/menuBackground.png", WIDTH, HEIGHT, false,
			true);
	final private Font customFont = Font.loadFont(
			StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(),
			HEIGHT / 12);

	@FXML
	private AnchorPane game;

	GameController(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.scene = primaryStage.getScene();
		inputs.put(CharacterActions.UP, false);
		inputs.put(CharacterActions.DOWN, false);
		inputs.put(CharacterActions.LEFT, false);
		inputs.put(CharacterActions.RIGHT, false);
		inputs.put(CharacterActions.ATTACK, false);
	}

	@FXML
	void initialize() {
		game.setPrefSize(WIDTH, HEIGHT);

		text.setFont(customFont);
		text.setFill(Color.DARKORANGE);
		text.setStroke(Color.DARKRED);
		text.setStrokeWidth(HEIGHT / 360);
		text.setLayoutX(WIDTH / 2 - text.getLayoutBounds().getWidth() / 2);
		text.setLayoutY(HEIGHT / 2 + text.getLayoutBounds().getHeight() / 2);

		resumeButton.setBackground(new Background(new BackgroundImage(
						new Image("images/resumeButton.png", game.getPrefWidth() / 3, game.getPrefHeight() / 9,
								false, false),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT)));
		resumeButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
		resumeButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
		resumeButton.setLayoutY(HEIGHT * 0.25 - resumeButton.getPrefHeight() / 2);
		resumeButton.setOnAction(e -> {
			pause = false;
			pause(gc);
		});

		quitButton.setBackground(new Background(new BackgroundImage(
						new Image("images/quitButton.png", game.getPrefWidth() / 3, game.getPrefHeight() / 9,
								false, false),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT)));
		quitButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
		quitButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
		quitButton.setLayoutY(HEIGHT * 0.75 - resumeButton.getPrefHeight() / 2);
		quitButton.setOnAction(e -> System.exit(0));

		muteButton.setBackground(new Background(new BackgroundImage(new Image("images/unmutedButton.png",
				HEIGHT / 15, HEIGHT / 15, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		muteButton.setPrefSize(HEIGHT / 15, HEIGHT / 15);
		muteButton.setLayoutX(WIDTH - muteButton.getPrefWidth());
		muteButton.setLayoutY(HEIGHT - muteButton.getPrefHeight());
		muteButton.setOnAction(actionEvent -> music.muteAction(muteButton));
		music.start();
		start(this.primaryStage);
	}

	@Override
	public void start(Stage stage) {
		game.getChildren().add(canvas);
		starter();

		scene.setOnMousePressed(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				inputs.replace(CharacterActions.ATTACK, true);
			}
		});

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case R:
				starter();
				break;
				case A:
					mainCharacter.attack(gc);
					break;
			case M :
				map.fullScreenMap(gc, mainCharacter.getPosition());
				mapShown = !mapShown;
				break;
			case ESCAPE:
				if (mainCharacter.getHealth() <= 0) {
					System.exit(0);
				} else if (mapShown) {
					mapShown = false;
				} else {
					pause = !pause;
					if (!pause) {
						pause(gc);
					}
				}
				break;
			case UP:
				inputs.replace(CharacterActions.UP, true);
				break;
			case DOWN:
				inputs.replace(CharacterActions.DOWN, true);
				break;
			case LEFT:
				inputs.replace(CharacterActions.LEFT, true);
				break;
			case RIGHT:
				inputs.replace(CharacterActions.RIGHT, true);
				break;
			case Z:
				map.moveNorth();
				mainCharacter.setMap(map);
				break;
			case S:
				map.moveSouth();
				mainCharacter.setMap(map);
				break;
			case Q:
				map.moveEast();
				mainCharacter.setMap(map);
				break;
			case D:
				map.moveWest();
				mainCharacter.setMap(map);
				break;
			case SHIFT:
				mainCharacter.startRun();
				break;
			case SPACE:
				mainCharacter.dash();
				break;
			default:
			}
		});

		scene.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case UP:
				inputs.replace(CharacterActions.UP, false);
				break;
			case DOWN:
				inputs.replace(CharacterActions.DOWN, false);
				break;
			case LEFT:
				inputs.replace(CharacterActions.LEFT, false);
				break;
			case RIGHT:
				inputs.replace(CharacterActions.RIGHT, false);
				break;
				case SHIFT:
					mainCharacter.stopRun();
					break;
			default:
			}
		});

		new AnimationTimer() {
			long lastNow = 0;
			final FPSMeter fpsmeter = new FPSMeter();
			public void handle(long now) {
				if (now - lastNow >= 15000000 && !mapShown) {
					if (!pause && mainCharacter.getHealth() > 0) {
						gc.fillRect(0, 0, WIDTH, HEIGHT);

						mainCharacter.update(inputs,map.getCurrent().getMonsters(), gc);

						map.updateMonster();
						map.display(gc, mainCharacter.getPosition());
						map.displayMiniMap(gc, mainCharacter.getPosition());

						mainCharacter.display(gc);
						mainCharacter.drawHitbox(gc, mainCharacter);
						mainCharacter.displayStatus(gc);

						fpsmeter.update(now, gc);
						lastNow = now;
					} else if (!pause && mainCharacter.getHealth() <= 0) {
						gameOver();
					} else if (!pauseShown) {
						pause(gc);
					}
				}
			}
		}.start();
	}

	private void pause(GraphicsContext gc) {
		if (pause) {
			gc.drawImage(pauseBackground, 0, 0);
			game.getChildren().add(quitButton);
			game.getChildren().add(resumeButton);
			game.getChildren().add(muteButton);
			game.getChildren().add(text);
			pauseShown = true;
		} else {
			game.getChildren().remove(quitButton);
			game.getChildren().remove(resumeButton);
			game.getChildren().remove(muteButton);
			game.getChildren().remove(text);
			pauseShown = false;
		}
	}

	private void gameOver() {
		if (!gameover) {
			gc.drawImage(pauseBackground, 0, 0);
			text.setText("Game Over press ESC to quit");
			text.setLayoutX(WIDTH / 2 - text.getLayoutBounds().getWidth() / 2);
			text.setLayoutY(HEIGHT / 2 + text.getLayoutBounds().getHeight() / 2);
			game.getChildren().add(text);
			gameover = true;
		}
	}

	private void starter() {
		mainCharacter = new MainCharacter(new Coordinate(0,0),map);
		this.map = new Map(20, mainCharacter);
		mainCharacter.startPosition(map.getCurrent());
		mainCharacter.setMap(map);
	}
}
