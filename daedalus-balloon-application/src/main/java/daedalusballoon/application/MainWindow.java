package daedalusballoon.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Daedalus Balloon Station");
        primaryStage.setScene(new ConfigMenu().makeScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}