package org.vfl.algorithms;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import com.google.protobuf.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.repository.DeliveryTruckRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrTools extends VrpSolver{
    @Autowired
    DeliveryTruckRepository deliveryTruckRepository;

    public List<Route> solve(List<Address> unfulfilledOrders, int days) {
        Loader.loadNativeLibraries();
        // Initialiseer schedule
        List<Route> schedule = new ArrayList<>();

        // Genereer een planning voor het aantal aangegeven dagen
        for(int i = 0; i < days; i++) {
            LocalDate start = LocalDate.now().plusDays(i);
            List<Route> daySchedule = solveForOneDay(unfulfilledOrders, deliveryTruckRepository.findAll(), start);

            // Add day schedule to overal schedule
            schedule.addAll(daySchedule);

            // Remove all addresses that were planned for this day
            Set<Long> plannedAddressIds = daySchedule.stream()
                    .flatMap(route -> route.getRouteAddresses().stream())
                    .map(routeAddress -> routeAddress.getAddress().getId())
                    .collect(Collectors.toSet());
            // update unfulfilledOrders to only contain unplanned addresses
            unfulfilledOrders = unfulfilledOrders.stream()
                    .filter(address -> !plannedAddressIds.contains(address.getId()))
                    .collect(Collectors.toList());
        }
        return schedule;
    }

    public List<Route> solveForOneDay(
            List<Address> unfulfilledOrders,
            List<DeliveryTruck> availableTrucks,
            LocalDate date
    ) {
        List<Route> daySchedule = new ArrayList<>();
        List<Address> withDepot = new ArrayList<>();

        withDepot.add(WINDESHEIM_DEPOT); // Depot op index 0
        withDepot.addAll(unfulfilledOrders);

        long[][] distanceMatrix = getDistanceMatrix(withDepot);

        int numVehicles = availableTrucks.size();
        int depot = 0;

        RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, numVehicles, depot);
        RoutingModel routing = new RoutingModel(manager);

        // Kostenfunctie: afstand = tijd in minuten (60 km/h). Add 15 min service time.
        final int transitCallbackIndex = routing.registerTransitCallback((fromIndex, toIndex) -> {
            int fromNode = manager.indexToNode(fromIndex);
            int toNode = manager.indexToNode(toIndex);
            return distanceMatrix[fromNode][toNode] + 15;
        });
        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        // Max tijd per route = 480 minuten (8 uur)
        routing.addDimension(transitCallbackIndex, 0, 480, true, "Time");
        routing.getMutableDimension("Time");

        // Voeg Disjunctions toe: klanten mogen worden overgeslagen met penalty
        int penalty = 500;
        for (int node = 1; node < distanceMatrix.length; node++) {
            routing.addDisjunction(new long[]{manager.nodeToIndex(node)}, penalty);
        }

        // Zoekparameters
        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters()
                .toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.SIMULATED_ANNEALING)
                .setTimeLimit(Duration.newBuilder().setSeconds(1).build())
                .build();

        Assignment solution = routing.solveWithParameters(searchParameters);
        if (solution != null) {
            for (int i = 0; i < numVehicles; i++) {
                long index = routing.start(i);
                List<Address> routeAddresses = new ArrayList<>();

                while (!routing.isEnd(index)) {
                    int nodeIndex = manager.indexToNode((int) index);
                    if (nodeIndex != depot) {
                        routeAddresses.add(withDepot.get(nodeIndex));
                    }
                    index = solution.value(routing.nextVar(index));
                }

                if (!routeAddresses.isEmpty()) {
                    Route route = saveRoute(date, availableTrucks.get(i), routeAddresses);
                    daySchedule.add(route);
                }
            }
        }
        return daySchedule;
    }
}