package daedalusballoon.application;

import daedalusballoon.core.FlightPathPredictor;
import daedalusballoon.core.WeatherBalloon;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

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

        Path configPath = Paths.get(System.getProperty("user.home"), "Daedalus");
        if(!Files.exists(configPath) || !Files.isDirectory(configPath)) {
            try {
                Files.createDirectories(configPath);
                Files.createFile(Paths.get(configPath.toString(), "weatherballoon.properties"));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(configPath.toString() + "/weatherballoon.properties"));
            CommandCenter.wb = new WeatherBalloon(props);
        } catch(IOException e) {
            e.printStackTrace();
        }

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