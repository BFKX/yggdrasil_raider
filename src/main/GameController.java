package main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;

class GameController {

    private Scene scene;

    @FXML private Text text;
    @FXML private AnchorPane game;

    @FXML void initialize() {

        double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        game.setPrefSize(WIDTH, HEIGHT);

        text.setFont(new Font("Monospace", game.getPrefHeight() / 20));
        text.setText("\\o/ GAME LAUNCHED \\o/\n Press ESCAPE to leave");
        text.setFill(Color.BLACK);
        text.setLayoutX((game.getPrefWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setLayoutY((game.getPrefHeight() + text.getLayoutBounds().getHeight()) / 2);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent e) {

                if(e.getCode() == KeyCode.ESCAPE) {

                    System.exit(0);
                }
            }
        });
    }

    void setScene(Scene scene) {

        this.scene = scene;
    }
}
