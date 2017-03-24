package daedalusballoon.application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class CommandCenter implements SceneMaker {

    @Override
    public Scene makeScene() {
        BorderPane borderpane = new BorderPane();

        Pane top = new Pane();
        Pane prompt = new Pane();

        borderpane.setTop(top);
        borderpane.setBottom(prompt);

        Scene scene = new Scene(borderpane, 640, 480);
        return scene;
    }
}