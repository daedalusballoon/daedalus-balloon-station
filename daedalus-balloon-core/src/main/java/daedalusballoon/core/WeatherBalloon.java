package daedalusballoon.core;

import java.util.Properties;

public class WeatherBalloon {

    private double weight;
    private double ascendRate;
    private double descendRate;
    private double burstAlt;

    public WeatherBalloon() {
    }

    public WeatherBalloon(Properties props) {
        String weight = props.getProperty("weight");
        if(weight != null)
            this.weight = Double.parseDouble(weight);

        String ascendRate = props.getProperty("ascent_rate");
        if(ascendRate != null)
            this.ascendRate = Double.parseDouble(ascendRate);

        String descendRate = props.getProperty("descent_rate");
        if(descendRate != null)
            this.descendRate = Double.parseDouble(descendRate);

        String burstAlt = props.getProperty("burst_alt");
        if(burstAlt != null)
            this.burstAlt = Double.parseDouble(burstAlt);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAscendRate() {
        return ascendRate;
    }

    public void setAscendRate(double ascendRate) {
        this.ascendRate = ascendRate;
    }

    public double getDescendRate() {
        return descendRate;
    }

    public void setDescendRate(double descendRate) {
        this.descendRate = descendRate;
    }

    public double getBurstAlt() {
        return burstAlt;
    }

    public void setBurstAlt(double burstAlt) {
        this.burstAlt = burstAlt;
    }
}