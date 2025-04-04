package AlgoritmTest;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbor implements TSPAlgorithm {
    public List<Integer> findShortestRoute(double[][] distanceMatrix) {
        int n = distanceMatrix.length;
        List<Integer> route = new ArrayList<>();
        boolean[] visited = new boolean[n];
        int current = 0;
        route.add(current);
        visited[current] = true;

        for (int i = 1; i < n; i++) {
            int nearest = -1;
            double minDist = Double.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distanceMatrix[current][j] < minDist) {
                    minDist = distanceMatrix[current][j];
                    nearest = j;
                }
            }
            if (nearest != -1) {
                route.add(nearest);
                visited[nearest] = true;
                current = nearest;
            }
        }

        route.add(0); // Return to starting city
        return route;
    }
}
