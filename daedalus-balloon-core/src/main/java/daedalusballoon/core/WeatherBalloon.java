package daedalusballoon.core;

public class WeatherBalloon {

    private double weight;
    private double ascendRate;
    private double descendRate;

    public WeatherBalloon(double weight, double ascendRate, double descendRate) {
        this.weight = weight;
        this.ascendRate = ascendRate;
        this.descendRate = descendRate;
    }
}