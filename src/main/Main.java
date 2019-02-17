package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Yggdrasil Raider");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("startMenuView.fxml"));

        StartMenuController startMenuController = new StartMenuController();

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
