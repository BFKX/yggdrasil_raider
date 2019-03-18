package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mapping.Map;
import tools.CharacterActions;
import tools.Coordinate;
import tools.FPSMeter;
import character.Character;
import tools.Hitbox;

import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;

class GameController extends Application {

    private Scene scene;
    private Stage primaryStage;
    private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private boolean pause = false;
    private final Image pauseBackground;
    private boolean pauseShown = false;
    private Button resumeButton = new Button("Resume");
    private Button quitButton = new Button("Quit");
    private Text text = new Text("Pause");
    private int fillPercentage = 50;
    private HashMap<CharacterActions, Boolean> inputs = new HashMap<>();

    @FXML private AnchorPane game;

    GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.pauseBackground = new Image("resources/images/menuBackground.png", WIDTH, HEIGHT, false, true);
        inputs.put(CharacterActions.UP, false);
        inputs.put(CharacterActions.DOWN, false);
        inputs.put(CharacterActions.LEFT, false);
        inputs.put(CharacterActions.RIGHT, false);
    }

    @FXML void initialize() throws Exception {
        game.setPrefSize(WIDTH, HEIGHT);

        Font titleFont = Font.loadFont(StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(),
                HEIGHT / 12);

        text.setFont(titleFont);
        text.setFill(Color.DARKORANGE);
        text.setStroke(Color.DARKRED);
        text.setStrokeWidth(HEIGHT / 360);
        text.setLayoutX(WIDTH / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(HEIGHT / 2 + text.getLayoutBounds().getHeight() / 2);

        resumeButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
        resumeButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
        resumeButton.setLayoutY(HEIGHT * 0.25- resumeButton.getPrefHeight() / 2);
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                pause = false;
                pauseShown = false;
                game.getChildren().remove(quitButton);
                game.getChildren().remove(resumeButton);
                game.getChildren().remove(text);
            }
        });

        quitButton.setPrefSize(WIDTH / 3, HEIGHT / 9);
        quitButton.setLayoutX((WIDTH - resumeButton.getPrefWidth()) / 2);
        quitButton.setLayoutY(HEIGHT * 0.75- resumeButton.getPrefHeight() / 2);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {

            }
        });
        start(this.primaryStage);
    }

    void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        game.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Map map = new Map(19*5, 11*5);
        //map.addGround();

        Character charac = new Character(new Coordinate(WIDTH / 2, HEIGHT / 2), new Hitbox(new Coordinate(0, 0), 0, 0));

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                switch(e.getCode()) {
                    case R: map.createCave(fillPercentage); break;
                    case P: fillPercentage++; break;
                    case M: fillPercentage--; break;
                    case ESCAPE: pause = !pause;
                        if(!pause) {
                            pauseShown = false;
                            game.getChildren().remove(quitButton);
                            game.getChildren().remove(resumeButton);
                            game.getChildren().remove(text);
                        }
                        break;
                    case UP: inputs.replace(CharacterActions.UP, true); break;
                    case DOWN: inputs.replace(CharacterActions.DOWN, true); break;
                    case LEFT: inputs.replace(CharacterActions.LEFT, true); break;
                    case RIGHT: inputs.replace(CharacterActions.RIGHT, true); break;
                    default:
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch(e.getCode()) {
                    case UP: inputs.replace(CharacterActions.UP, false); break;
                    case DOWN: inputs.replace(CharacterActions.DOWN, false); break;
                    case LEFT: inputs.replace(CharacterActions.LEFT, false); break;
                    case RIGHT: inputs.replace(CharacterActions.RIGHT, false); break;
                    default:
                }
            }
        });

        new AnimationTimer() {
            long lastNow = 0;

            FPSMeter fpsm = new FPSMeter();

            public void handle(long now) {
                if(!pause && now - lastNow >= 15000000) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, WIDTH, HEIGHT);

                    inputs = charac.displacement(inputs);

                    map.display(gc);

                    charac.displayCharacter(gc);

                    fpsm.update(now, gc);

                    lastNow = now;

                    gc.setFill(Color.CHARTREUSE);
                    gc.setFont(Font.font("Helvetica", FontWeight.BOLD, HEIGHT / 50));
                    gc.fillText(Integer.toString((int)fillPercentage), WIDTH - 25 * WIDTH / 1000, HEIGHT / 60);
                } else if(!pauseShown && now - lastNow >= 15000000){
                    gc.drawImage(pauseBackground, 0, 0);
                    game.getChildren().add(quitButton);
                    game.getChildren().add(resumeButton);
                    game.getChildren().add(text);
                    pauseShown = true;
                }
            }
        }.start();
    }
}
