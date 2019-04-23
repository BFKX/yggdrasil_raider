package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	private final Stage primaryStage;
	private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	@FXML
	private Button playButton;
	@FXML
	private Button quitButton;
	@FXML
	private Button muteButton;
	@FXML
	private AnchorPane startMenu;
	@FXML
	private Text title;

	StartMenuController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	void initialize() {
		startMenu.setPrefSize(WIDTH, HEIGHT);
		Font titleFont = Font.loadFont(
				StartMenuController.class.getResource("../resources/fonts/VIKING-N.TTF").toExternalForm(),
				startMenu.getPrefHeight() / 12);
		MusicPlayer music = new MusicPlayer("/resources/audio/startMenu.wav");
		startMenu.setBackground(new Background(new BackgroundImage(
				new Image("images/menuBackground.png", WIDTH, HEIGHT, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

		title.setFont(titleFont);
		title.setFill(Color.DARKORANGE);
		title.setStroke(Color.DARKRED);
		title.setStrokeWidth(startMenu.getPrefHeight() / 360);
		title.setText("Yggdrasil Raider");
		title.setLayoutX(startMenu.getPrefWidth() / 2 - title.getLayoutBounds().getWidth() / 2);
		title.setLayoutY(startMenu.getPrefHeight() / 2 + title.getLayoutBounds().getHeight() / 2);

		playButton.setBackground(new Background(new BackgroundImage(
				new Image("images/playButton.png", startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9, false,
						false),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		playButton.setPrefSize(startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9);
		playButton.setLayoutX((startMenu.getPrefWidth() - playButton.getPrefWidth()) / 2);
		playButton.setLayoutY(startMenu.getPrefHeight() * 0.25 - playButton.getPrefHeight() / 2);
		playButton.setDefaultButton(true);
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/gameView.fxml"));
				loader.setController(new GameController(primaryStage));
				music.stop();
				try {
					primaryStage.getScene().setRoot(loader.load());
				} catch (IOException err) {
					err.printStackTrace();
					System.exit(1);
				}
			}
		});

		quitButton.setBackground(new Background(new BackgroundImage(
				new Image("images/quitButton.png", startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9, false,
						false),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		quitButton.setPrefSize(startMenu.getPrefWidth() / 3, startMenu.getPrefHeight() / 9);
		quitButton.setLayoutX((startMenu.getPrefWidth() - quitButton.getPrefWidth()) / 2);
		quitButton.setLayoutY(startMenu.getPrefHeight() * 0.75 - quitButton.getPrefHeight() / 2);
		quitButton.setOnAction(e -> System.exit(0));

		muteButton.setBackground(new Background(new BackgroundImage(
				new Image("images/muteee.png", HEIGHT / 10, HEIGHT / 10, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		muteButton.setPrefSize(HEIGHT / 10, HEIGHT / 10);
		muteButton.setLayoutX(WIDTH - muteButton.getPrefWidth());
		muteButton.setLayoutY(HEIGHT - muteButton.getPrefHeight());
		muteButton.setOnAction(actionEvent -> music.stop());
		music.start();
	}
}
