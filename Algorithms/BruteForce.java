package Algorithms;

import java.util.ArrayList;
import java.util.List;

public class BruteForce implements TSPAlgorithm {
    public List<Integer> findShortestRoute(double[][] distanceMatrix) {
        int n = distanceMatrix.length;
        List<Integer> cities = new ArrayList<>();

        // Add all cities (except city 0) to the list
        for (int i = 1; i < n; i++) {
            cities.add(i);
        }

        // Variables to store the best route and the minimum distance
        List<Integer> bestRoute = null;
        double bestDistance = Double.MAX_VALUE;

        // Generate all permutations of cities and calculate the distance
        List<List<Integer>> permutations = generatePermutations(cities);
        for (List<Integer> perm : permutations) {
            // Add the start and end city (city 0)
            List<Integer> route = new ArrayList<>();
            route.add(0);
            route.addAll(perm);
            route.add(0);

            // Calculate the distance of the current route
            double distance = calculateTotalDistance(route, distanceMatrix);

            // If the current distance is the best, update bestRoute and bestDistance
            if (distance < bestDistance) {
                bestDistance = distance;
                bestRoute = new ArrayList<>(route);
            }
        }

        // Return the best route found
        return bestRoute;
    }

    // Method to generate all permutations of a list of cities
    private List<List<Integer>> generatePermutations(List<Integer> cities) {
        List<List<Integer>> permutations = new ArrayList<>();
        if (cities.size() == 1) {
            permutations.add(new ArrayList<>(cities));
            return permutations;
        }

        for (int i = 0; i < cities.size(); i++) {
            // Remove the current city from the list
            List<Integer> remainingCities = new ArrayList<>(cities);
            remainingCities.remove(i);

            // Recursively generate permutations for the remaining cities
            List<List<Integer>> subPermutations = generatePermutations(remainingCities);

            // Add the current city to the front of each permutation
            for (List<Integer> subPerm : subPermutations) {
                subPerm.add(0, cities.get(i));
                permutations.add(subPerm);
            }
        }

        return permutations;
    }

    // Method to calculate the total distance for a given route
    private double calculateTotalDistance(List<Integer> route, double[][] distanceMatrix) {
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += distanceMatrix[route.get(i)][route.get(i + 1)];
        }
        return total;
    }
}
