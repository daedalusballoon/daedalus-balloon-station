package daedalusballoon.core;

import java.util.LinkedList;

public class FlightPath {

    private LinkedList<GPSCoord> ascendingCoords;
    private LinkedList<GPSCoord> descendingCoords;

    public FlightPath() {
        ascendingCoords = new LinkedList<GPSCoord>();
        descendingCoords = new LinkedList<GPSCoord>();
    }

    public void addAscendingCoord(double lat, double lon) {
        ascendingCoords.add(new GPSCoord(lat, lon));
    }

    public LinkedList<GPSCoord> getAscendingCoords() {
        return ascendingCoords;
    }

    public void addDescendingCoord(double lat, double lon) {
        descendingCoords.add(new GPSCoord(lat, lon));
    }

    public LinkedList<GPSCoord> getDescendingCoords() {
        return descendingCoords;
    }

    public boolean hasSafeLanding() {
        GPSCoord landing = descendingCoords.getLast();
        return !GPSCoord.isInWater(landing.getLat(), landing.getLon(), GPSCoord.Strategy.GOOGLE_MAPS);
    }
}