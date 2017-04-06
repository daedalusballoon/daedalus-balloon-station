package daedalusballoon.application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.event.*;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Locale;

public class CommandCenter implements SceneMaker, MapComponentInitializedListener {

    private GoogleMapView mapView;
    private GoogleMap map;

    @Override
    public Scene makeScene() {
        BorderPane borderpane = new BorderPane();

        Pane top = new Pane();
        HBox hbox = new HBox();
        Pane infoPanel = new Pane();
        infoPanel.getChildren().add(new Label("Daedalus Balloon"));

        mapView = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapView.addMapInializedListener(this);
        mapView.setMaxHeight(MainWindow.getStageHeight()/2);
        mapView.setMaxWidth(MainWindow.getStageWidth()*.85);
        hbox.getChildren().addAll(infoPanel, mapView);
        top.getChildren().add(hbox);

        Double promptHeight = MainWindow.getStageHeight()/2;
        Pane prompt = new Pane();
        TextArea console = new TextArea();
        VBox vbox = new VBox();
        console.setPrefHeight(promptHeight);
        vbox.setPrefWidth(MainWindow.getStageWidth());
        vbox.setPrefHeight(promptHeight);
        vbox.getChildren().add(console);
        prompt.getChildren().add(vbox);

        console.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    console.setText("");
                }
            }
        });

        borderpane.setTop(top);
        borderpane.setBottom(prompt);

        Scene scene = new Scene(borderpane, 640, 480);
        return scene;
    }

    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(25.757362, -80.370596))
                .mapMarker(true)
                .zoom(13)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .mapType(MapTypeIdEnum.TERRAIN);
        map = mapView.createMap(mapOptions, false);

    }
}