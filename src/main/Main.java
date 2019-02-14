package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Yggdrasil Raider");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Parent root;
        try {

            root = FXMLLoader.load(getClass().getResource("startMenu.fxml"));
        } catch (IOException e) {

            e.printStackTrace();
            return;
        }

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
