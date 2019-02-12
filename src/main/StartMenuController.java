package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button playButton;

    @FXML
    private Button quitButton;

    @FXML
    private AnchorPane myPane;

    @FXML
    private Text myText;

    @FXML
    private Text title;

    @FXML
    void initialize() {

        double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        myPane.setPrefSize(WIDTH, HEIGHT);

        Image background = new Image("resources/graphics/startMenuBackground.png", WIDTH, HEIGHT, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        myPane.setBackground(new Background(backgroundImage));

        Media sound = new Media(new File("r/home/bertaga/Documents/Java/yggdrasil_raider/src/resources/sounds/startMenuMusic.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        title.setFont(new Font("Noto Sans CJK KR DemiLight", myPane.getPrefHeight() / 12));
        title.setFill(Color.GREEN);
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(myPane.getPrefHeight() / 360);
        title.setText("Yggdrasil Raider");
        title.setLayoutX(myPane.getPrefWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setLayoutY(myPane.getPrefHeight()/12 + title.getLayoutBounds().getHeight() / 2);

        System.out.println(javafx.scene.text.Font.getFamilies());


        playButton.setPrefSize(myPane.getPrefWidth() / 3, myPane.getPrefHeight() / 9);
        playButton.setLayoutX((myPane.getPrefWidth() - playButton.getPrefWidth()) / 2);
        playButton.setLayoutY(myPane.getPrefHeight() * 0.25- playButton.getPrefHeight() / 2);

        quitButton.setPrefSize(myPane.getPrefWidth() / 3, myPane.getPrefHeight() / 9);
        quitButton.setLayoutX((myPane.getPrefWidth() - quitButton.getPrefWidth()) / 2);
        quitButton.setLayoutY(myPane.getPrefHeight() * 0.75 - quitButton.getPrefHeight() / 2);

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myText.setFont(new Font("Monospace", myPane.getPrefHeight() / 20));
                myText.setText("Game launched");
                myText.setFill(Color.INDIANRED);
                myText.setLayoutX((myPane.getPrefWidth() - myText.getLayoutBounds().getWidth()) / 2);
                myText.setLayoutY((myPane.getPrefHeight() + myText.getLayoutBounds().getHeight()) / 2);
            }
        });
    }
}
