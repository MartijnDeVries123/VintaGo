package org.vfl.algorithms;

import java.util.ArrayList;
import java.util.List;

public class LinKernighanAlgorithm {
    private static final double EARTH_RADIUS_KM = 6371.0; // needed for the Haversine Formula later on

    public List<Destination> calculateRoute(List<Destination> input) {
        List<Destination> bestRoute = new ArrayList<>(input);
        double bestDistance = calculateAllDistances(bestRoute);
        boolean improvement = true;

        while (improvement) {
            improvement = false;

            // 2-opt move, gets 2 elements of the route and swap places to check if the route will be shorter.
            for (int i = 0; i < bestRoute.size() - 2; ++i) {
                for (int x = i + 2; x < bestRoute.size() - 1; ++x) {
                    List<Destination> newRoute = reverseSublist(bestRoute, i + 1, x);
                    double newDistance = calculateAllDistances(newRoute);
                    if (newDistance < bestDistance) { // check if new distance is shorter
                        bestRoute = newRoute;
                        bestDistance = newDistance;
                        improvement = true; // once this is false again the best route has been found
                    }
                }
            }
        }
        return bestRoute;
    }

    //This wil function will reverse te order of the given start and end adresses
    private List<Destination> reverseSublist(List<Destination> route, int start, int end) {
        List<Destination> newRoute = new ArrayList<>(route.subList(0, start));

        // Reverse loop so we can add the last point first en the first point last so the positions will be reversed.
        for (int i = end; i >= start; --i) {
            newRoute.add(route.get(i));
        }
        newRoute.addAll(route.subList(end + 1, route.size()));
        return newRoute;
    }

    // this calculates the distance between 2 points
    private double calculateAllDistances(List<Destination> route) {
        double distance = 0.0;

        for (int i = 0; i < route.size() - 1; ++i) {
            Destination source = route.get(i);
            Destination target = route.get(i + 1);
            distance += distanceHelperHaversineDistance(source, target);
        }
        //back to start
        distance += distanceHelperHaversineDistance(route.get(route.size() - 1), route.get(0)); // adds the distance from end to start so we create the hamilton tour

        return distance;
    }

    //This will calculate the distance between 2 points using the Haversine Formula
    private double distanceHelperHaversineDistance(Destination source, Destination target) {
        double lat1 = Math.toRadians(source.getLatitude());
        double lon1 = Math.toRadians(source.getLongitude());
        double lat2 = Math.toRadians(target.getLatitude());
        double lon2 = Math.toRadians(target.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    // this is using the Euclidean formula to calculate the distance.
    private double distanceHelperEuclideanDistance(Destination source, Destination target) {
        double latitudeDifference = source.getLatitude() - target.getLatitude(); //
        double longitudeDifference = source.getLongitude() - target.getLongitude();
        return Math.sqrt(latitudeDifference * latitudeDifference + longitudeDifference * longitudeDifference); // magic math
    }
}

//Model i used, can be removed for implementation
public class Destination {
    private String name;
    private double latitude;
    private double longitude;

    public Destination(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;

    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
