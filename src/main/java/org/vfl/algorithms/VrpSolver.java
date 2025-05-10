package org.vfl.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.vintago.entity.*;
import org.vfl.vintago.repository.RouteRepository;
import org.vfl.vintago.util.DistanceMatrix;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public abstract class VrpSolver {
    @Autowired RouteRepository routeRepository;

    protected static final Address WINDESHEIM_DEPOT;
    static {
        Address depot = new Address();
        depot.setStreet("Campus 2");
        depot.setZip("8017CA");
        depot.setCity("Zwolle");
        depot.setLat(52.4964);
        depot.setLng(6.0846);
        WINDESHEIM_DEPOT = depot;
    }

    protected long[][] getDistanceMatrix(List<Address> addresses) {
        return DistanceMatrix.getDistanceMatrix(addresses);
    }
    protected Map<Address, Integer> mapAddressToIndex(List<Address> addresses) {
        Map<Address, Integer> addressToIndex = new HashMap<>();
        for (int i = 0; i < addresses.size(); i++) {
            addressToIndex.put(addresses.get(i), i);
        }
        return addressToIndex;
    }

    public abstract List<Route> solve(List<Address> unfulfilledOrders, int days);

    // Todo. Dit moet verplaatst worden naar een eigen service. Een LoggingService zou bij uitstek geschikt zijn.
//    default List<Integer> findShortestRouteTimingMethod(double[][] distanceMatrix) {
//        long startTime = System.nanoTime();
//        double elapsedTimeMs = 0;
//        List<Integer> shortestRoute = findShortestRoute(distanceMatrix);
//        long endTime = System.nanoTime();
//        long elapsedTimeNs = endTime - startTime;
//
//        if (elapsedTimeNs < 1_000_000) {
//            elapsedTimeMs = 0;
//        } else {
//            elapsedTimeMs = elapsedTimeNs / 1_000_000.0;
//        }
//
//        System.out.println("\nExecution time: " + (elapsedTimeNs) + "ns = " + String.format("%.1f", elapsedTimeMs) + "ms");
//        return shortestRoute;
//    }
    protected Route saveRoute(
            LocalDate deliveryDate,
            DeliveryTruck deliveryTruck,
            List<Address> solvedAddresses
    ) {
        Route route = new Route();
        route.setDeliveryDate(deliveryDate);
        route.setDeliveryTruck(deliveryTruck);

        List<RouteAddress> routeAddresses = new ArrayList<>();
        for (int i = 0; i < solvedAddresses.size(); i++) {
            if (solvedAddresses.get(i).equals(WINDESHEIM_DEPOT)) continue;

            Address address = solvedAddresses.get(i);
            address.setStatus("pending");

            RouteAddress routeAddress = new RouteAddress();
            routeAddress.setRoute(route);
            routeAddress.setAddress(address);
            routeAddress.setStepOrder(i + 1);

            RouteAddressId id = new RouteAddressId();
            id.setRouteId(null); // Wordt na persist ingevuld
            id.setAddressId(address.getId());
            routeAddress.setId(id);

            routeAddresses.add(routeAddress);
        }

        route.setRouteAddresses(routeAddresses);

        routeRepository.save(route);
        return route;
    }

    protected double calculateTotalDistance(List<Address> route, Map<Address, Integer> addressToIndex, long[][] distanceMatrix) {
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int fromIndex = addressToIndex.get(route.get(i));
            int toIndex = addressToIndex.get(route.get(i + 1));
            total += distanceMatrix[fromIndex][toIndex];
        }
        return total;
    }
}
