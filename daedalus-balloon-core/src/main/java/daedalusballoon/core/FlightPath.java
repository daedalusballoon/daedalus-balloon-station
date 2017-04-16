package daedalusballoon.core;

import java.util.LinkedList;

public class FlightPath {

    private LinkedList<GPSCoord> ascendingCoords;
    private LinkedList<GPSCoord> descendingCoords;
    private double totalDistance;

    public FlightPath() {
        ascendingCoords = new LinkedList<GPSCoord>();
        descendingCoords = new LinkedList<GPSCoord>();
        totalDistance = -1;
    }

    public void addAscendingCoord(double lat, double lon) {
        ascendingCoords.add(new GPSCoord(lat, lon));
    }

    public LinkedList<GPSCoord> getAscendingCoords() {
        return ascendingCoords;
    }

    public void addDescendingCoord(double lat, double lon) {
        descendingCoords.add(new GPSCoord(lat, lon));
    }

    public LinkedList<GPSCoord> getDescendingCoords() {
        return descendingCoords;
    }

    public GPSCoord getLaunchCoord() {
        return ascendingCoords.getFirst();
    }

    public boolean hasSafeLanding() {
        GPSCoord landing = descendingCoords.getLast();
        return !GPSCoord.isInWater(landing.getLat(), landing.getLon(), GPSCoord.Strategy.GOOGLE_MAPS);
    }

    //finds the closest point on the path to another point
    public GPSCoord closestPathPoint(GPSCoord coord) {
        GPSCoord minDisCoord = ascendingCoords.getFirst();
        double minDistance = GPSCoord.distance(minDisCoord, coord);
        for(GPSCoord acoord : ascendingCoords) {
            if(GPSCoord.distance(acoord, coord) < minDistance) {
                minDisCoord = acoord;
                minDistance = GPSCoord.distance(acoord, coord);
            }
        }
        for(GPSCoord dcoord : descendingCoords) {
            if(GPSCoord.distance(dcoord, coord) < minDistance) {
                minDisCoord = dcoord;
                minDistance = GPSCoord.distance(dcoord, coord);
            }
        }
        return minDisCoord;
    }

    public double totalDistance() {
        if(totalDistance == -1)
            return totalDistance = subpathDist(descendingCoords.getLast());
        else
            return totalDistance;
    }

    public double subpathDist(GPSCoord coord) {
        GPSCoord prevcoord = ascendingCoords.getFirst();
        GPSCoord currcoord = ascendingCoords.getFirst();
        double distance = 0.0;
        for(GPSCoord acoord : ascendingCoords) {
            currcoord = acoord;
            if(coord == currcoord)
                return distance;
            distance += GPSCoord.distance(prevcoord, currcoord);
            prevcoord = currcoord;
        }
        prevcoord = descendingCoords.getFirst();
        for(GPSCoord dcoord : descendingCoords) {
            currcoord = dcoord;
            if(coord == currcoord)
                return distance;
            distance += GPSCoord.distance(prevcoord, currcoord);
            prevcoord = currcoord;
        }
        return -1;
    }
}