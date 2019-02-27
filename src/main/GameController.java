package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    @FXML private Text text;
    @FXML private AnchorPane game;

    GameController(Stage primaryStage) {

        this.primaryStage = primaryStage;

        this.pauseBackground = new Image("resources/images/menuBackground.png", WIDTH, HEIGHT, false, false);
    }

    @FXML void initialize() throws Exception {

        game.setPrefSize(WIDTH, HEIGHT);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent e) {

                if(e.getCode() == KeyCode.ESCAPE) {

                    pause = !pause;
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
                } else {

                    gc.drawImage(pauseBackground, 0, 0);
                }

                fpsm.update(now, gc);
            }
        }.start();
    }
}
