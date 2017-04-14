package daedalusballoon.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Networking {
    private Networking() {}

    public static String getReq(String apiURL) throws IOException {
        URL url = new URL(apiURL);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String strTemp = "";
        StringBuilder response = new StringBuilder();

        while (null != (strTemp = br.readLine())) {
            response.append(strTemp);
        }

        return response.toString();
    }
}