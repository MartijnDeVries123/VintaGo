package org.vfl.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.repository.DeliveryTruckRepository;
import org.vfl.vintago.service.ClusterOrdersService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class LinKernighanAlgorithm extends VrpSolver {
    @Autowired DeliveryTruckRepository deliveryTruckRepository;
    @Autowired ClusterOrdersService clusterOrdersService;

    Random random = new Random();

    public List<Route> solve(List<Address> unfulfilledOrders, int days) {
        List<Route> schedule = new ArrayList<>();
        List<DeliveryTruck> deliveryTrucks = deliveryTruckRepository.findAll();
        LocalDate start = LocalDate.now();

        clusterOrdersService.setClusterSize(unfulfilledOrders.size() / 10);
        List<List<Address>> clusters = clusterOrdersService.clusterOrders(unfulfilledOrders);

        // Create routes per day * amount trucks available
        for (int i = 0; i < days; i++) {
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

    private List<Address> solveForOneRoute(List<Address> bestRoute) {
        List<Address> withDepot = new ArrayList<>();
        withDepot.add(WINDESHEIM_DEPOT);
        withDepot.addAll(bestRoute);

        long[][] distanceMatrix = getDistanceMatrix(withDepot);
        Map<Address, Integer> addressToIndex = mapAddressToIndex(withDepot);

        List<Address> routeFull = new ArrayList<>();
        routeFull.add(WINDESHEIM_DEPOT);
        routeFull.addAll(bestRoute);
        routeFull.add(WINDESHEIM_DEPOT);

        double bestDistance = calculateTotalDistance(routeFull, addressToIndex, distanceMatrix);
        boolean improvement = true;

        while (improvement) {
            improvement = false;

            // 2-opt move, gets 2 elements of the route and swap places to check if the route will be shorter.
            for (int i = 0; i < bestRoute.size() - 2; ++i) {
                for (int x = i + 2; x < bestRoute.size() - 1; ++x) {
                    List<Address> newRoute = reverseSublist(bestRoute, i + 1, x);

                    List<Address> routeNewFull = new ArrayList<>();
                    routeNewFull.add(WINDESHEIM_DEPOT);
                    routeNewFull.addAll(newRoute);
                    routeNewFull.add(WINDESHEIM_DEPOT);

                    double newDistance = calculateTotalDistance(routeNewFull, addressToIndex, distanceMatrix);
                    if (newDistance < bestDistance) {
                        bestRoute = newRoute;
                        bestDistance = newDistance;
                        improvement = true;
                    }
                }
            }
        }
        return bestRoute;
    }

    //This wil function will reverse te order of the given start and end adresses
    private List<Address> reverseSublist(List<Address> route, int start, int end) {
        List<Address> newRoute = new ArrayList<>(route.subList(0, start));

        // Reverse loop so we can add the last point first en the first point last so the positions will be reversed.
        for (int i = end; i >= start; --i) {
            newRoute.add(route.get(i));
        }
        newRoute.addAll(route.subList(end + 1, route.size()));
        return newRoute;
    }
}
