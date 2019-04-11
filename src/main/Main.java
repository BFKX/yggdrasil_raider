package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        StartMenuController startMenuController = new StartMenuController(primaryStage);
        loader.setController(startMenuController);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        startMenuController.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
