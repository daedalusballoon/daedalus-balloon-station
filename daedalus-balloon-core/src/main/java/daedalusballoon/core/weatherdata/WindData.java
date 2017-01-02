package daedalusballoon.core.weatherdata;

public class WindData {

    private float windStr;
    private String windDir;

    public WindData(float windStr, String windDir) {
        this.windStr = windStr;
        this.windDir = windDir;
    }

    public float getWindStrength() {
        return windStr;
    }

    public String getWindDirection() {
        return windDir;
    }

}