package tools;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
	private MediaPlayer player;
	private boolean running = true;
	final private Background mutedButton;
	final private Background unmutedButton;
	private Button muteButton;

	public MusicPlayer(String path, double side, Button muteButton) {
		setPath(path);
		mutedButton = new Background(new BackgroundImage(new Image("images/mutedButton.png", side, side, false, false),
				BackgroundRepeat.NO_REPEAT,	BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
		unmutedButton = new Background(new BackgroundImage(new Image("images/unmutedButton.png", side, side, false, false),
				BackgroundRepeat.NO_REPEAT,	BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
		this.muteButton = muteButton;
	}

	public void setPath(String path) {
		player = new MediaPlayer(new Media(getClass().getResource(path).toString()));
		player.setVolume((running) ? 1 : 0);
		player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
	}

	public void start() {
		player.play();
		running = true;
	}

	public void stop() {
		player.stop();
		running = false;
	}

	public void muteAction(Button muteButton) {
		if(running) {
			player.setVolume(0);
			running = false;
			muteButton.setBackground(mutedButton);
		} else {
			player.setVolume(1);
			running = true;
			muteButton.setBackground(unmutedButton);
		}
	}

	public Button getMuteButton() {
		return muteButton;
	}
}