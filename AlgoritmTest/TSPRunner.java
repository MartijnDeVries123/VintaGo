package AlgoritmTest;

public class TSPRunner {
    public static void main(String[] args) {
        double[][] distanceMatrix = {
                {0, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60},
                {10, 0, 35, 25, 40, 20, 30, 45, 55, 65, 75, 85},
                {15, 35, 0, 30, 50, 45, 55, 65, 75, 85, 95, 105},
                {20, 25, 30, 0, 55, 35, 60, 70, 80, 90, 100, 110},
                {25, 40, 50, 55, 0, 60, 70, 80, 90, 100, 110, 120},
                {30, 20, 45, 35, 60, 0, 75, 85, 95, 105, 115, 125},
                {35, 30, 55, 60, 70, 75, 0, 95, 105, 115, 125, 135},
                {40, 45, 65, 70, 80, 85, 95, 0, 115, 125, 135, 145},
                {45, 55, 75, 80, 90, 95, 105, 115, 0, 135, 145, 155},
                {50, 65, 85, 90, 100, 105, 115, 125, 135, 0, 155, 165},
                {55, 75, 95, 100, 110, 115, 125, 135, 145, 155, 0, 175},
                {60, 85, 105, 110, 120, 125, 135, 145, 155, 165, 175, 0}
        };


/*        TSPAlgorithm nearestNeighbor = new NearestNeighbor();
        TSPAlgorithm simulatedAnnealing = new SimulatedAnnealing();

        System.out.println("Nearest Neighbor Route: " + nearestNeighbor.findShortestRoute(distanceMatrix));
        System.out.println("Simulated Annealing Route: " + simulatedAnnealing.findShortestRoute(distanceMatrix));
*/
        String[] algoritms = {"Nearest Neighbor", "Simulated Annealing", "Brute Force"};
        for (String algorithm : algoritms) {
            TSPAlgorithm tspChoice = getAlgorithmInstance(algorithm);
            System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRouteTimingMethod(distanceMatrix));
        }

/*
        TSPAlgorithm tspChoice = getAlgorithmInstance(algorithm);

        //System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRoute(distanceMatrix));
        System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRouteTimingMethod(distanceMatrix));

        algorithm = "Simulated Annealing";
        tspChoice = getAlgorithmInstance(algorithm);
        System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRouteTimingMethod(distanceMatrix));

        algorithm = "Brute Force";
        tspChoice = getAlgorithmInstance(algorithm);
        System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRouteTimingMethod(distanceMatrix));
*/

    }

    private static TSPAlgorithm getAlgorithmInstance(String algorithm) {
        if (algorithm.equals("Nearest Neighbor")) {
            return new NearestNeighbor();
        } else if (algorithm.equals("Simulated Annealing")) {
            return new SimulatedAnnealing();
        } else if (algorithm.equals("Brute Force")) {
            return new BruteForce();
        } else {
            return null;
        }
    }
}
