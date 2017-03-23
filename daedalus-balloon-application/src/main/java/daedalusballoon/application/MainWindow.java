package daedalusballoon.application;

import javafx.application.Application;
import javafx.stage.Stage;

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