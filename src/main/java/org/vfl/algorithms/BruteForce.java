package org.vfl.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vfl.vintago.entity.*;
import org.vfl.vintago.repository.DeliveryTruckRepository;
import org.vfl.vintago.service.ClusterOrdersService;

import java.time.LocalDate;
import java.util.*;

@Component
public class BruteForce extends VrpSolver {
    @Autowired
    ClusterOrdersService clusterOrdersService;
    @Autowired
    DeliveryTruckRepository deliveryTruckRepository;

    Random random = new Random();

    public List<Route> solve(List<Address> unfulfilledOrders, int days) {
        List<Route> schedule = new ArrayList<>();
        List<DeliveryTruck> deliveryTrucks = deliveryTruckRepository.findAll();
        LocalDate start = LocalDate.now();

        clusterOrdersService.setClusterSize(unfulfilledOrders.size() / 3);
        List<List<Address>> clusters = clusterOrdersService.clusterOrders(unfulfilledOrders);

        // Create routes per day * amount trucks available
        for (int i = 0; i < 2; i++) {
            for (DeliveryTruck deliveryTruck : deliveryTrucks) {
                // Get random cluster from clusters
                if (! clusters.isEmpty() ) {
                    List<Address> randomUnfulfilledCluster = clusters.get(random.nextInt(clusters.size()));
                    // Solve TSP for this cluster
                    List<Address> solvedAddresses = this.solveForOneRoute(randomUnfulfilledCluster);
                    // Remove the solved cluster from clusters, so it can't be used again
                    clusters.remove(randomUnfulfilledCluster);
                    // Save route to database
                    Route route = saveRoute(start.plusDays(i), deliveryTruck, solvedAddresses);
                    // Add route to schedule
                    schedule.add(route);
                }
            }
        }
        return schedule;
    }

    // Method to generate all permutations of a list of cities
    private List<List<Address>> generatePermutations(List<Address> addresses) {
        List<List<Address>> permutations = new ArrayList<>();
        if (addresses.size() == 1) {
            permutations.add(new ArrayList<>(addresses));
            return permutations;
        }

        for (int i = 0; i < addresses.size(); i++) {
            // Remove the current city from the list
            List<Address> remainingCities = new ArrayList<>(addresses);
            remainingCities.remove(i);

            // Recursively generate permutations for the remaining cities
            List<List<Address>> subPermutations = generatePermutations(remainingCities);

            // Add the current city to the front of each permutation
            for (List<Address> subPerm : subPermutations) {
                subPerm.add(0, addresses.get(i));
                permutations.add(subPerm);
            }
        }
        return permutations;
    }

    public List<Address> solveForOneRoute(List<Address> unfulfilledOrders) {
        List<Address> withDepot = new ArrayList<>();
        withDepot.add(WINDESHEIM_DEPOT);
        withDepot.addAll(unfulfilledOrders);


        long[][] distanceMatrix = getDistanceMatrix(withDepot);
        Map<Address, Integer> addressToIndex = mapAddressToIndex(withDepot);

        List<Address> bestRoute = null;
        double bestDistance = Double.MAX_VALUE;

        // Generate all permutations of orders and calculate the distance
        List<List<Address>> permutations = generatePermutations(unfulfilledOrders);
        for (List<Address> perm : permutations) {
            List<Address> route = new ArrayList<>();
            route.add(WINDESHEIM_DEPOT);
            route.addAll(perm);
            route.add(WINDESHEIM_DEPOT);

            // Calculate the distance of the current route
            double distance = calculateTotalDistance(route, addressToIndex, distanceMatrix);

            // If the current distance is the best, update bestRoute and bestDistance
            if (distance < bestDistance) {
                bestDistance = distance;
                bestRoute = new ArrayList<>(route);
            }
        }
        return bestRoute;
    }
}
