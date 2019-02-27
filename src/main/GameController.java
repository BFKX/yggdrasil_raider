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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tools.FPSMeter;

import java.awt.Toolkit;

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

    @FXML private Text text;
    @FXML private AnchorPane game;

    GameController(Stage primaryStage) {

        this.primaryStage = primaryStage;

        this.pauseBackground = new Image("resources/images/menuBackground.png", WIDTH, HEIGHT, false, true);
    }

    @FXML void initialize() throws Exception {

        game.setPrefSize(WIDTH, HEIGHT);

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

                if(e.getCode() == KeyCode.ESCAPE) {

                    pause = !pause;
                    if(!pause) {

                        pauseShown = false;
                        game.getChildren().remove(quitButton);
                        game.getChildren().remove(resumeButton);
                    }
                }
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

        new AnimationTimer() {

            FPSMeter fpsm = new FPSMeter();

            public void handle(long now) {

                if(!pause) {

                    gc.setFill(Color.BLACK);
                    gc.fillRect(0, 0, WIDTH, HEIGHT);

                    fpsm.update(now, gc);
                } else if(!pauseShown){

                    gc.drawImage(pauseBackground, 0, 0);
                    game.getChildren().add(quitButton);
                    game.getChildren().add(resumeButton);
                    pauseShown = true;
                }
            }
        }.start();
    }
}
