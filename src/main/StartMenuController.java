package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tools.MusicPlayer;

import java.awt.Toolkit;
import java.io.IOException;

class StartMenuController {

    private Scene scene;
    private Stage primaryStage;
    private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    @FXML private Button playButton;
    @FXML private Button quitButton;
    @FXML private AnchorPane startMenu;
    @FXML private Text title;

    StartMenuController(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }

    @FXML void initialize() throws Exception {

        MusicPlayer music = new MusicPlayer("/resources/audio/startMenuMusic.wav");
        music.start();

        startMenu.setPrefSize(WIDTH, HEIGHT);

        final BackgroundImage backgroundImage = new BackgroundImage(new Image("images/menuBackground.png", WIDTH,
                HEIGHT, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        startMenu.setBackground(new Background(backgroundImage));

        Font titleFont = Font.loadFont(StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(),
                startMenu.getPrefHeight() / 12);

        title.setFont(titleFont);
        title.setFill(Color.DARKORANGE);
        title.setStroke(Color.DARKRED);
        title.setStrokeWidth(startMenu.getPrefHeight() / 360);
        title.setText("Yggdrasil Raider");
        title.setLayoutX(startMenu.getPrefWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setLayoutY(startMenu.getPrefHeight()/2 + title.getLayoutBounds().getHeight() / 2);

        playButton.setPrefSize(startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9);
        playButton.setLayoutX((startMenu.getPrefWidth() - playButton.getPrefWidth()) / 2);
        playButton.setLayoutY(startMenu.getPrefHeight() * 0.25- playButton.getPrefHeight() / 2);

        quitButton.setPrefSize(startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9);
        quitButton.setLayoutX((startMenu.getPrefWidth() - quitButton.getPrefWidth()) / 2);
        quitButton.setLayoutY(startMenu.getPrefHeight() * 0.75 - quitButton.getPrefHeight() / 2);

        quitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                System.exit(0);
            }
        });

        playButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("gameView.fxml"));

                GameController gameController = new GameController(primaryStage);
                gameController.setScene(scene);

                loader.setController(gameController);

                music.stop();

                try {

                    scene.setRoot(loader.load());
                } catch(IOException err) {

                    err.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

    void setScene(Scene scene) {

        this.scene = scene;
    }
}
