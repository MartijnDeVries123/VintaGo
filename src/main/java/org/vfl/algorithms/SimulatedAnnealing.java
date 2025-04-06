package org.vfl.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing implements TSPAlgorithm {
    private static final double COOLING_RATE = 0.995;
    private static final double INITIAL_TEMPERATURE = 1000;

    @Override
    public List<Integer> findShortestRoute(double[][] distanceMatrix) {
        int n = distanceMatrix.length;
        List<Integer> currentRoute = getRandomRoute(n);
        double currentDistance = calculateTotalDistance(currentRoute, distanceMatrix);
        List<Integer> bestRoute = new ArrayList<>(currentRoute);
        double bestDistance = currentDistance;
        double temperature = INITIAL_TEMPERATURE;

        Random rand = new Random();

        while (temperature > 1) {
            List<Integer> newRoute = new ArrayList<>(currentRoute);
            int i = rand.nextInt(n - 1) + 1;
            int j = rand.nextInt(n - 1) + 1;
            Collections.swap(newRoute, i, j);

            double newDistance = calculateTotalDistance(newRoute, distanceMatrix);
            if (newDistance < currentDistance || Math.exp((currentDistance - newDistance) / temperature) > rand.nextDouble()) {
                currentRoute = new ArrayList<>(newRoute);
                currentDistance = newDistance;
                if (newDistance < bestDistance) {
                    bestRoute = new ArrayList<>(newRoute);
                    bestDistance = newDistance;
                }
            }
            temperature *= COOLING_RATE;
        }
        return bestRoute;
    }

    private List<Integer> getRandomRoute(int n) {
        List<Integer> route = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            route.add(i);
        }
        Collections.shuffle(route);
        route.add(route.get(0)); // Return to start
        return route;
    }

    private double calculateTotalDistance(List<Integer> route, double[][] distanceMatrix) {
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += distanceMatrix[route.get(i)][route.get(i + 1)];
        }
        return total;
    }
}
