package daedalusballoon.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightPathPredictor {

    public enum Strategy {
        CUSF
    }

    private Strategy strategy;

    public FlightPathPredictor(Strategy strategy) {
        this.strategy = strategy;
    }

    public String predictPath(double lat, double lon, double burst) {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(new Date());
        try {
            String res = Networking.getReq("http://predict.cusf.co.uk/api/v1/?launch_latitude=" + lat + "&launch_longitude=" + lon +
                    "&launch_datetime=" + date + "&ascent_rate=5&descent_rate=10&burst_altitude=" + burst);
            return res;
        } catch(IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}