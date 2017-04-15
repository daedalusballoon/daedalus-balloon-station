package daedalusballoon.core;

import java.io.IOException;

import org.json.*;

public class GPSCoord {

    private double lat, lon;

    // Approximate Earth Radius in km
    private static final double R = 6378.137;

    public GPSCoord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public enum Strategy {
        GOOGLE_MAPS
    }

    public static double distance(GPSCoord coord1, GPSCoord coord2) {
        double latDiff = Math.toRadians(coord2.getLat() - coord1.getLat());
        double lonDiff = Math.toRadians(coord2.getLon() - coord1.getLon());
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(coord1.getLat())) * Math.cos(Math.toRadians(coord2.getLat()))
                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in kilometers
    }

    public static boolean isInWater(double lat, double lon, Strategy strategy) {
        try {
            double lowLat = latOffset(lat, -15);
            double lowLon = lonOffset(lon, -15, lat);
            double highLat = latOffset(lat, 15);
            double highLon = lonOffset(lon, 15, lat);
            String res = Networking.getReq("http://maps.google.com/maps/api/geocode/json?address="+
                    lat+","+lon+"&bounds="+lowLat+","+lowLon+"|"+highLat+","+highLon);
            JSONObject obj = new JSONObject(res);
            String status = obj.getString("status");
            int l = ((JSONObject)obj.getJSONArray("results").get(0)).getJSONArray("address_components").length();
            if(status.equals("ZERO_RESULTS"))
                return true;
            if(l < 3)
                return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static double latOffset(double lat, double km) {
        double m = km * 1000;
        return lat + (m/R) * (180/Math.PI);
    }

    private static double lonOffset(double lon, double km, double lat) {
        double m = km * 1000;
        double dlon = (m/(R*Math.cos(Math.PI*lat/180)));
        return lon + dlon * 180/Math.PI;
    }

    @Override
    public String toString() {
        return "Latitude: " + lat + ", Longitude: " + lon;
    }
}