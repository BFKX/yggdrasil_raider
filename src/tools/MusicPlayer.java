package tools;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {
	private MediaPlayer player;

	/**
	 * @param path path to the resource
	 */
	public MusicPlayer(String path) {
		setPath(path);
	}

	/**
	 * @param path path to the resource
	 */
	private void setPath(String path) {
		player = new MediaPlayer(new Media(getClass().getResource(path).toString()));
		player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
	}

	public void start() {
		player.play();
	}

	public void stop() {
		player.stop();
	}
}