package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapping.Map;
import characters.Monster;
import characters.RandomPathMonster;
import org.jetbrains.annotations.NotNull;
import tools.*;
import characters.MainCharacter;
import characters.MonsterTest;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

class GameController extends Application {
	final private double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	final private double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final Scene scene;
	private final Stage primaryStage;
	private boolean pause = false;
	private boolean pauseShown = false;
	private final Button resumeButton = new Button();
	private final Button quitButton = new Button();
	private final Button muteButton = new Button();
	private final Text text = new Text("Pause");
	private final HashMap<CharacterActions, Boolean> inputs = new HashMap<>();
	private final MusicPlayer music = new MusicPlayer("/resources/audio/inGame.wav", HEIGHT / 15);
	private Map map = new Map(200 + ThreadLocalRandom.current().nextInt(-50, 50),
			200 + ThreadLocalRandom.current().nextInt(-50, 50));
	final private Image pauseBackground = new Image("resources/images/menuBackground.png", WIDTH, HEIGHT, false, true);
	final private Font customFont = Font.loadFont(
			StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(), HEIGHT / 12);
	private Monster monster ;

	@FXML
	private AnchorPane game;

	GameController(@NotNull Stage primaryStage) {
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

		resumeButton
				.setBackground(new Background(new BackgroundImage(
						new Image("images/resumeButton.png", game.getPrefWidth() / 3, game.getPrefHeight() / 9, false,
								false),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT)));
		resumeButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
		resumeButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
		resumeButton.setLayoutY(HEIGHT * 0.25 - resumeButton.getPrefHeight() / 2);
		resumeButton.setOnAction(e -> {
			pause = false;
			pauseShown = false;
			game.getChildren().remove(quitButton);
			game.getChildren().remove(resumeButton);
			game.getChildren().remove(muteButton);
			game.getChildren().remove(text);
		});

		quitButton
				.setBackground(new Background(new BackgroundImage(
						new Image("images/quitButton.png", game.getPrefWidth() / 3, game.getPrefHeight() / 9, false,
								false),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
						BackgroundSize.DEFAULT)));
		quitButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
		quitButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
		quitButton.setLayoutY(HEIGHT * 0.75 - resumeButton.getPrefHeight() / 2);
		quitButton.setOnAction(e -> System.exit(0));

		muteButton.setBackground(new Background(new BackgroundImage(new Image("images/unmutedButton.png", HEIGHT / 15, HEIGHT / 15, false, false), BackgroundRepeat.NO_REPEAT,
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
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		game.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		MainCharacter mainCharacter = new MainCharacter(new Coordinate(WIDTH / 2, HEIGHT / 2),map);
        MonsterTest monsterr[] = new MonsterTest[20];
        MonsterTest boss = new MonsterTest(new Coordinate(100 + Math.random() * 50,100 + Math.random() * 50),mainCharacter.getPosition(),map,4.0);
        for(int i = 0;i < 20;i++)
		{
			monsterr[i] = new MonsterTest(new Coordinate(Math.random()*150,Math.random()*150),mainCharacter.getPosition(),map,Math.random() * 3);
		}

		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case R:
				map = new Map(200 + ThreadLocalRandom.current().nextInt(-50, 50),
						200 + ThreadLocalRandom.current().nextInt(-50, 50));
				for(int i = 0;i < 20;i++)
				{
					monsterr[i] = new MonsterTest(new Coordinate(Math.random()*150,Math.random()*150),mainCharacter.getPosition(),map,Math.random() * 3);
				}
				mainCharacter.setMap(map);
				break;
			case M :
				 monster = new RandomPathMonster(new Coordinate(ThreadLocalRandom.current().nextInt(100, 150),
						ThreadLocalRandom.current().nextInt(100,150)),mainCharacter.getPosition(),map);
				break;
			case ESCAPE:
				pause = !pause;
				if (!pause) {
					pauseShown = false;
					game.getChildren().remove(quitButton);
					game.getChildren().remove(resumeButton);
					game.getChildren().remove(muteButton);
					game.getChildren().remove(text);
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
				case A:
					inputs.replace(CharacterActions.ATTACK, true);
					break;
			case Z:
				map.moveNorth();
				break;
			case S:
				map.moveSouth();
				break;
			case Q:
				map.moveEast();
				break;
			case D:
				map.moveWest();
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
				case A:
					inputs.replace(CharacterActions.ATTACK, false);
					break;
			default:
			}
		});

		new AnimationTimer() {
			long lastNow = 0;
			final FPSMeter fpsmeter = new FPSMeter();
			public void handle(long now) {
				if (!pause && now - lastNow >= 15000000) {
					gc.setFill(Color.BLACK);
					gc.fillRect(0, 0, WIDTH, HEIGHT);

					mainCharacter.update(inputs);

					map.display(gc, mainCharacter.getPosition());
					map.displayMiniMap(gc, mainCharacter.getPosition());

					mainCharacter.display(gc);
					mainCharacter.drawHitbox(gc);
					if ( monster != null) {
						monster.updateDisplacement();
						monster.display(gc);
						mainCharacter.setPosition(monster.getPositionInt());
					}
					for(int i = 0;i<20;i++)
					{
						monsterr[i].updateDisplacement();
						//monsterr[i].display(gc,mainCharacter.getPosition());
						monsterr[i].valueOflife(inputs);
						//monsterr[i].drawhitbox(gc);
					}
				//	boss.display(gc,mainCharacter.getPosition());
					fpsmeter.update(now, gc);
					lastNow = now;


				} else if (!pauseShown && now - lastNow >= 15000000) {
					gc.drawImage(pauseBackground, 0, 0);
					game.getChildren().add(quitButton);
					game.getChildren().add(resumeButton);
					game.getChildren().add(muteButton);
					game.getChildren().add(text);
					pauseShown = true;
				}
			}
		}.start();
	}
}
