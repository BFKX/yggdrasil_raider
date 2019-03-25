package tools;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MusicPlayer {

    private MediaPlayer player;

    public MusicPlayer(String path) {

        player = new MediaPlayer(new Media(getClass().getResource(path).toString()));

        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
    }

    public void start() {
        player.play();
    }

    public void stop() {
        player.stop();
    }
}