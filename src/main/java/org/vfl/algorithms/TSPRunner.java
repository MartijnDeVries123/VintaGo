package org.vfl.algorithms;

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

        String[][] randomAddresses = {
                {"J.M. de Muinck Keizerlaan", "32", "Utrecht"},
                {"Verryn Stuartweg", "45", "Goes"},
                {"Kuipersdijk", "165", "Enschede"},
                {"Neptunusplein","44","Amersfoort"},
                {"Kryptonweg","11","Utrecht"},
                {"Morsstraat","9","Leiden"},
                {"Staalindustrieweg","19","Alblasserdam"},
                {"Energieweg","102","Nijmegen"},
        };

        String[] algoritms = {"Nearest Neighbor", "Simulated Annealing", "Brute Force"};
        for (String algorithm : algoritms) {
            TSPAlgorithm tspChoice = getAlgorithmInstance(algorithm);
            System.out.println("TSP Algorithm: " + algorithm + tspChoice.findShortestRouteTimingMethod(distanceMatrix));
        }
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
