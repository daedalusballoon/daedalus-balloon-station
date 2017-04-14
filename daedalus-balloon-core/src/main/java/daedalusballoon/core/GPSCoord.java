package daedalusballoon.core;

import java.io.IOException;

import org.json.*;

public class GPSCoord {

    private double lat, lon;

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

    // Approximate Earth Radius
    private static final double R = 6378137;

    private static double latOffset(double lat, double km) {
        double m = km * 1000;
        return lat + (m/R) * (180/Math.PI);
    }

    private static double lonOffset(double lon, double km, double lat) {
        double m = km * 1000;
        double dlon = (m/(R*Math.cos(Math.PI*lat/180)));
        return lon + dlon * 180/Math.PI;
    }
}