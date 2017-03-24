package daedalusballoon.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Daedalus Balloon Station");
        primaryStage.setScene(new ConfigMenu().makeScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(Scene scene) {
        primaryStage.setScene(scene);
        //primaryStage.show();
    }
}