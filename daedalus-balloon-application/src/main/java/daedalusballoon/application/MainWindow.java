package daedalusballoon.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Daedalus Balloon");

        StackPane root = new StackPane();
        root.getChildren().add(title);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Daedalus Balloon Station");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}