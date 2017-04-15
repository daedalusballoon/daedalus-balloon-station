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
import java.util.ArrayList;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
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

import daedalusballoon.core.FlightPath;
import daedalusballoon.core.FlightPathPredictor;
import daedalusballoon.core.GPSCoord;
import org.json.*;

public class CommandCenter implements SceneMaker, MapComponentInitializedListener {

    private GoogleMapView mapView;
    private GoogleMap map;
    private FlightPathPredictor flp;

    //GUI components
    private TextArea console;

    private Marker inputMarker;

    public CommandCenter() {
        flp = new FlightPathPredictor(FlightPathPredictor.Strategy.CUSF);
    }

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
        console = new TextArea();
        TextField input = new TextField();
        VBox vbox = new VBox();
        console.setPrefHeight(promptHeight*0.8);
        input.setPrefHeight(promptHeight*0.1);
        vbox.setPrefWidth(MainWindow.getStageWidth());
        vbox.setPrefHeight(promptHeight);
        vbox.getChildren().addAll(console, input);
        prompt.getChildren().add(vbox);

        input.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String[] inputargs = input.getText().split("\\s+");
                    switch(inputargs[0]) {
                        case "launch": createPrediction(Double.parseDouble(inputargs[1]),
                                                        Double.parseDouble(inputargs[2])); break;
                        default : appendConsole("Unrecognized command"); break;
                    }
                    input.setText("");
                } else if(keyEvent.getCode() == KeyCode.TAB) {

                    //Ends the tab key event to prevent from tab keyevent to lose focus of the textfield
                    keyEvent.consume();
                }
            }
        });

        borderpane.setTop(top);
        borderpane.setBottom(prompt);

        Scene scene = new Scene(borderpane, 640, 480);
        return scene;
    }

    private void createPrediction(double lat, double lon) {
        map.clearMarkers();
        FlightPath fp = flp.predictPath(lat, lon, 30000);
        addAllMarkers(fp.getAscendingCoords());
        addAllMarkers(fp.getDescendingCoords());
        appendConsole("FlightPath is " + (fp.hasSafeLanding() ? "safe" : "not safe") );
    }

    private void appendConsole(String str) {
        console.setText(console.getText() + str + "\n");
    }

    private void addAllMarkers(List<GPSCoord> coords) {
        for(GPSCoord coord : coords)
            addMarker(coord.getLat(), coord.getLon());
    }

    private void addMarker(double lat, double lon) {
        MarkerOptions markerOpts = new MarkerOptions();
        markerOpts.position(new LatLong(lat, lon))
                .visible(true);
        Marker marker = new Marker(markerOpts);
        map.addMarker(marker);
    }

    private void setInputMarker(double lat, double lon) {
        inputMarker.setPosition(new LatLong(lat, lon));
        refreshMap();
    }

    private void refreshMap() {
        map.setZoom(map.getZoom()+1);
        map.setZoom(map.getZoom()-1);
    }

    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(52.2135, 0.0964))
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

        map.addMouseEventHandler(null, UIEventType.click, new MouseEventHandler() {
            public void handle(GMapMouseEvent event) {
                LatLong latLong = event.getLatLong();
                setInputMarker(latLong.getLatitude(), latLong.getLongitude());
            }
        });

        MarkerOptions markerOpts = new MarkerOptions();
        markerOpts.visible(true);
        inputMarker = new Marker(markerOpts);
        map.addMarker(inputMarker);
    }
}