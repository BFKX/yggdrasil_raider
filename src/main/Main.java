package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent startMenu = FXMLLoader.load(getClass().getResource("startMenu.fxml"));

        primaryStage.setTitle("Yggdrasil Raider");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        primaryStage.setScene(new Scene(startMenu));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
