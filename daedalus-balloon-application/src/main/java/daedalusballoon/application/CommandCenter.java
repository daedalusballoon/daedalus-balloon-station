package daedalusballoon.application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.event.*;

import java.util.List;

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
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;

import java.util.Locale;

import daedalusballoon.core.FlightPath;
import daedalusballoon.core.FlightPathPredictor;
import org.json.*;

public class CommandCenter implements SceneMaker, MapComponentInitializedListener {

    private GoogleMapView mapView;
    private GoogleMap map;

    private FlightPath fp;
    private FlightPathPredictor flp;

    //GUI components
    private TextArea console;
    private TextField input;

    private Marker inputMarker;
    private double inputLat;
    private double inputLon;

    public CommandCenter() {
        flp = new FlightPathPredictor(FlightPathPredictor.Strategy.CUSF);
        console = new TextArea();
        input = new TextField();
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
                                                        Double.parseDouble(inputargs[2]),
                                                        30000); break;
                        case "adjust":
                            if(fp != null) {
                                daedalusballoon.core.Coord adjustPoint = fp.closestPathPoint(new daedalusballoon.core.Coord(
                                                            Double.parseDouble(inputargs[1]),
                                                            Double.parseDouble(inputargs[2])));
                                double ratio = fp.subpathDist(adjustPoint)/fp.totalDistance();
                                createPrediction(fp.getLaunchCoord().getLat(),
                                                fp.getLaunchCoord().getLon(),
                                                30000*ratio); break;
                            }
                            break;
                        default : appendConsole("Unrecognized command"); break;
                    }
                    input.setText("");
                } else if(keyEvent.getCode() == KeyCode.TAB) {
                    tabInput();
                    input.positionCaret(input.getText().length());
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

    private void createPrediction(double lat, double lon, double burstalt) {
        map.clearMarkers();
        fp = flp.predictPath(lat, lon, burstalt);
        int i = 0;
        LatLong[] gpsarr = new LatLong[fp.getAscendingCoords().size()+fp.getDescendingCoords().size()];
        for(daedalusballoon.core.Coord coord : fp.getAscendingCoords()) {
            gpsarr[i++] = new LatLong(coord.getLat(), coord.getLon());
        }
        for(daedalusballoon.core.Coord coord : fp.getDescendingCoords()) {
            gpsarr[i++] = new LatLong(coord.getLat(), coord.getLon());
        }
        MVCArray mvc = new MVCArray(gpsarr);
        PolylineOptions polyOpts = new PolylineOptions()
                .path(mvc)
                .strokeColor("red")
                .strokeWeight(2);
        Polyline poly = new Polyline(polyOpts);
        map.addMapShape(poly);
        appendConsole("FlightPath is " + (fp.hasSafeLanding() ? "safe" : "not safe") );
    }

    private void appendConsole(String str) {
        console.setText(console.getText() + str + "\n");
    }

    private void tabInput() {
        input.setText(input.getText() + " " + inputLat + " " + inputLon);
    }

    private void addAllMarkers(List<daedalusballoon.core.Coord> coords) {
        for(daedalusballoon.core.Coord coord : coords)
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
        inputLat = lat;
        inputLon = lon;
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