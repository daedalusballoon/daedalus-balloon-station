package daedalusballoon.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import daedalusballoon.core.weatherdata.WindData;

public class NOAAWeather {

    private final String baseUrl = "https://tidesandcurrents.noaa.gov/api/";

    private float lat, lon;

    private String stationId;

    private Units units;

    public enum Units {
        Metric,
        Imperial
    }

    public NOAAWeather(String stationId) {
        this.stationId = stationId;
        units = Units.Metric;
    }

    public NOAAWeather(String stationId, Units units) {
        this.stationId = stationId;
        this.units = units;
    }

    public void getRecentWindData() throws IOException {
        String units = this.units == Units.Metric ? "metric" : "english";
        Networking.getReq(baseUrl+"datagetter?product=wind&date=latest&station="+stationId+"&time_zone=LST_LDT&units="+units+"&format=json&application=DaedalusBase");
    }

}