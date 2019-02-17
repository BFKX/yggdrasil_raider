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

import java.awt.Toolkit;
import java.io.IOException;

public class StartMenuController {

    private Scene scene;

    @FXML private Button playButton;

    @FXML private Button quitButton;

    @FXML private AnchorPane pane;

    @FXML private Text title;

    @FXML void initialize() throws Exception {

        MusicPlayer music = new MusicPlayer("src/resources/sounds/startMenuMusic.wav");
        music.start();

        double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        pane.setPrefSize(WIDTH, HEIGHT);

        final BackgroundImage backgroundImage = new BackgroundImage(new Image("resources/graphics/startMenuBackground.png", WIDTH, HEIGHT, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        Font titleFont = Font.loadFont(StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(), pane.getPrefHeight() / 12);

        title.setFont(titleFont);
        title.setFill(Color.DARKORANGE);
        title.setStroke(Color.DARKRED);
        title.setStrokeWidth(pane.getPrefHeight() / 360);
        title.setText("Yggdrasil Raider");
        title.setLayoutX(pane.getPrefWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setLayoutY(pane.getPrefHeight()/2 + title.getLayoutBounds().getHeight() / 2);

        playButton.setPrefSize(pane.getPrefWidth() / 3, pane.getPrefHeight() / 9);
        playButton.setLayoutX((pane.getPrefWidth() - playButton.getPrefWidth()) / 2);
        playButton.setLayoutY(pane.getPrefHeight() * 0.25- playButton.getPrefHeight() / 2);

        quitButton.setPrefSize(pane.getPrefWidth() / 3, pane.getPrefHeight() / 9);
        quitButton.setLayoutX((pane.getPrefWidth() - quitButton.getPrefWidth()) / 2);
        quitButton.setLayoutY(pane.getPrefHeight() * 0.75 - quitButton.getPrefHeight() / 2);

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

                GameController gameController = new GameController();
                gameController.setScene(scene);

                loader.setController(gameController);

                try {

                    scene.setRoot(loader.load());
                } catch(IOException err) {

                    err.printStackTrace();
                    System.exit(1);
                }

            }
        });
    }

    public void setScene(Scene scene) {

        this.scene = scene;
    }
}
