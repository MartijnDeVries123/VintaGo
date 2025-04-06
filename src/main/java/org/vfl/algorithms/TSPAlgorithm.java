package org.vfl.algorithms;

import java.util.List;

public interface TSPAlgorithm {
    List<Integer> findShortestRoute(double[][] distanceMatrix);

    default List<Integer> findShortestRouteTimingMethod(double[][] distanceMatrix) {
        long startTime = System.nanoTime();
        double elapsedTimeMs = 0;
        List<Integer> shortestRoute = findShortestRoute(distanceMatrix);
        long endTime = System.nanoTime();
        long elapsedTimeNs = endTime - startTime;

        if (elapsedTimeNs < 1_000_000) {
            elapsedTimeMs = 0;
        } else {
            elapsedTimeMs = elapsedTimeNs / 1_000_000.0;
        }

        System.out.println("\nExecution time: " + (elapsedTimeNs) + "ns = " + String.format("%.1f", elapsedTimeMs) + "ms");
        return shortestRoute;
    }
}
