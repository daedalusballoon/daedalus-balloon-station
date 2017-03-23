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

public class ConfigMenu implements SceneMaker {
    public Scene makeScene() {
        VBox vb = new VBox();
        vb.setSpacing(10);
        vb.setAlignment(Pos.TOP_CENTER);

        TextField textField = new TextField();

        vb.getChildren().addAll(new Label("NOAA Station Id:"), textField);
        Button button = new Button("Test");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("PlaceHolder action");
            }
        });
        vb.getChildren().add(button);


        StackPane root = new StackPane();
        root.getChildren().add(vb);
        Scene scene = new Scene(root, 640, 480);
        return scene;
    }
}