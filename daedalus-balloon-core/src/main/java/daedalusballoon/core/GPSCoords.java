package daedalusballoon.core;

public class GPSCoords {

    private double lat, lon;

    public GPSCoords(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}