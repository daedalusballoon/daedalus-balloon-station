package daedalusballoon.core;

public class WeatherBalloon {

    private double weight;
    private double ascendRate;
    private double descendRate;
    private double burstAlt;

    public WeatherBalloon() {
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