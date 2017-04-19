package daedalusballoon.core;

import java.util.LinkedList;

public class FlightPath {

    private LinkedList<Coord> ascendingCoords;
    private LinkedList<Coord> descendingCoords;
    private double totalDistance;

    public FlightPath() {
        ascendingCoords = new LinkedList<Coord>();
        descendingCoords = new LinkedList<Coord>();
        totalDistance = -1;
    }

    public void addAscendingCoord(double lat, double lon) {
        ascendingCoords.add(new Coord(lat, lon));
    }

    public LinkedList<Coord> getAscendingCoords() {
        return ascendingCoords;
    }

    public void addDescendingCoord(double lat, double lon) {
        descendingCoords.add(new Coord(lat, lon));
    }

    public LinkedList<Coord> getDescendingCoords() {
        return descendingCoords;
    }

    public Coord getLaunchCoord() {
        return ascendingCoords.getFirst();
    }

    public boolean hasSafeLanding() {
        Coord landing = descendingCoords.getLast();
        return !Coord.isInWater(landing.getLat(), landing.getLon(), Coord.Strategy.GOOGLE_MAPS);
    }

    //finds the closest point on the path to another point
    public Coord closestPathPoint(Coord coord) {
        Coord minDisCoord = ascendingCoords.getFirst();
        double minDistance = Coord.distance(minDisCoord, coord);
        for(Coord acoord : ascendingCoords) {
            if(Coord.distance(acoord, coord) < minDistance) {
                minDisCoord = acoord;
                minDistance = Coord.distance(acoord, coord);
            }
        }
        for(Coord dcoord : descendingCoords) {
            if(Coord.distance(dcoord, coord) < minDistance) {
                minDisCoord = dcoord;
                minDistance = Coord.distance(dcoord, coord);
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

    public double subpathDist(Coord coord) {
        Coord prevcoord = ascendingCoords.getFirst();
        Coord currcoord = ascendingCoords.getFirst();
        double distance = 0.0;
        for(Coord acoord : ascendingCoords) {
            currcoord = acoord;
            if(coord == currcoord)
                return distance;
            distance += Coord.distance(prevcoord, currcoord);
            prevcoord = currcoord;
        }
        prevcoord = descendingCoords.getFirst();
        for(Coord dcoord : descendingCoords) {
            currcoord = dcoord;
            if(coord == currcoord)
                return distance;
            distance += Coord.distance(prevcoord, currcoord);
            prevcoord = currcoord;
        }
        return -1;
    }
}