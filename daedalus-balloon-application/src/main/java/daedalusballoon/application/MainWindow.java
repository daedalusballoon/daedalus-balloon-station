package daedalusballoon.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label title = new Label("Daedalus Balloon");

        Label label1 = new Label("NOAA Station Id:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);

        StackPane root = new StackPane();
        root.getChildren().add(title);
        root.getChildren().add(hb);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("Daedalus Balloon Station");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}