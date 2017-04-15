package daedalusballoon.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

public class FlightPathPredictor {

    public enum Strategy {
        CUSF
    }

    private Strategy strategy;

    public FlightPathPredictor(Strategy strategy) {
        this.strategy = strategy;
    }

    public FlightPath predictPath(double lat, double lon, double burst) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        try {

            String res = Networking.getReq("http://predict.cusf.co.uk/api/v1/?launch_latitude=" + lat + "&launch_longitude=" + lon +
                    "&launch_datetime=" + date + "&ascent_rate=5&descent_rate=10&burst_altitude=" + burst);
            FlightPath fp = new FlightPath();
            JSONObject jsonres = new JSONObject(res);
            JSONArray ascending = jsonres.getJSONArray("prediction").getJSONObject(0).getJSONArray("trajectory");
            JSONArray descending = jsonres.getJSONArray("prediction").getJSONObject(1).getJSONArray("trajectory");

            for(int i = 0; i < ascending.length(); i++) {
                double alat = ascending.getJSONObject(i).getDouble("latitude");
                double alon = ascending.getJSONObject(i).getDouble("longitude");
                fp.addAscendingCoord(alat, alon);
            }

            for(int i = 0; i < descending.length(); i++) {
                double dlat = descending.getJSONObject(i).getDouble("latitude");
                double dlon = descending.getJSONObject(i).getDouble("longitude");
                fp.addDescendingCoord(dlat, dlon);
            }

            return fp;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}