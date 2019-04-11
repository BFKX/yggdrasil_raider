package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        primaryStage.setTitle("Yggdrasil Raider");
        primaryStage.getIcons().add(new Image("resources/images/icon.png"));
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/startMenuView.fxml"));
        loader.setController(new StartMenuController(primaryStage));
        primaryStage.setScene(new Scene(loader.load()));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
