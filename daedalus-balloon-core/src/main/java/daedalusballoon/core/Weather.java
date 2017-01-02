package daedalusballoon.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import daedalusballoon.core.weatherdata.WindData;

public class Weather {

    public static final String baseUrl = "https://tidesandcurrents.noaa.gov/api/";

    public static Float lat, lon;

    public static String stationId;

    public static String units;

    private Weather() {}

    public static void NOAAWindData() {
        try {
            URL url = new URL(baseUrl+"datagetter?product=wind&date=latest&station=8723214&time_zone=LST_LDT&units=english&format=json&application=DaedalusBase");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp = "";
            while (null != (strTemp = br.readLine())) {
                System.out.println(strTemp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}