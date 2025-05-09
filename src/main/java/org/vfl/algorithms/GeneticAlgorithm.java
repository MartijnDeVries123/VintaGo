package org.vfl.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements TSPAlgorithm {
    private static final boolean debug = false;

    @Override
    public List<Integer> findShortestRoute(double[][] distanceMatrix) {
        int numberOfAddresses = distanceMatrix.length;
        int populationSize = Math.max(numberOfAddresses, Math.min(2 * numberOfAddresses, 1000));
        List<List<Integer>> population = initializePopulation(numberOfAddresses, populationSize);

        return null;
    }

    private List<List<Integer>> initializePopulation(int numberOfAddresses, int populationSize) {
        Random random = new Random();
        List<List<Integer>> population = new ArrayList<>();
        List<Integer> baseRoute = new ArrayList<>();

        // Add all indexes of Addresses as baseRoute
        for (int i = 0; i < numberOfAddresses; i++) {
            baseRoute.add(i);
        }
        if (debug) System.out.println("Baseroute: " + baseRoute);

        // Generate random permutations of the baseRoute (Fisher-Yates shuffle)
        for (int i = 0; i < populationSize; i++) {
            List<Integer> route = new ArrayList<>(baseRoute);
            for (int j = numberOfAddresses - 1; j > 0; j--) {
                int index = random.nextInt(j + 1);
                Integer temp = route.get(index);
                route.set(index, route.get(j));
                route.set(j, temp);
            }
            if (debug) System.out.println("Route: " + route);
            population.add(route);
        }
        return population;
    }
}