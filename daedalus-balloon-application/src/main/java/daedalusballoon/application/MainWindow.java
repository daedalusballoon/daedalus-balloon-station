package daedalusballoon.application;

import daedalusballoon.core.GPSInfo;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

        InvalidationListener resizeListener = new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {
            }
        };
        primaryStage.widthProperty().addListener(resizeListener);
        primaryStage.heightProperty().addListener(resizeListener);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public static double getStageWidth() {
        return primaryStage.getWidth();
    }

    public static double getStageHeight() {
        return primaryStage.getHeight();
    }

    public static void setStageSize(double w, double h) {
        primaryStage.setWidth(w);
        primaryStage.setHeight(h);
    }

}