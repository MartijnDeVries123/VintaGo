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
        depot.setStreet("Campus");
        depot.setNumber("2");
        depot.setZip("8017CA");
        depot.setCity("Zwolle");
        depot.setLat(52.4964);
        depot.setLng(6.0846);
        WINDESHEIM_DEPOT = depot;
    }

    public abstract List<Route> solve(List<Address> unfulfilledOrders, int days);

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

    protected Route saveRoute(
            LocalDate deliveryDate,
            DeliveryTruck deliveryTruck,
            List<Address> solvedAddresses
    ) {
        Route route = new Route();
        route.setDeliveryDate(deliveryDate);
        route.setDeliveryTruck(deliveryTruck);

        int stepOrder = 0;
        List<RouteAddress> routeAddresses = new ArrayList<>();
        for (Address solvedAddress : solvedAddresses) {
            if (solvedAddress.equals(WINDESHEIM_DEPOT)) continue;

            stepOrder++;
            solvedAddress.setStatus("pending");

            RouteAddress routeAddress = new RouteAddress();
            routeAddress.setRoute(route);
            routeAddress.setAddress(solvedAddress);
            routeAddress.setStepOrder(stepOrder);

            RouteAddressId id = new RouteAddressId();
            id.setRouteId(null); // Wordt na persist ingevuld
            id.setAddressId(solvedAddress.getId());
            routeAddress.setId(id);

            routeAddresses.add(routeAddress);
        }

        route.setRouteAddresses(routeAddresses);

        routeRepository.save(route);
        return route;
    }

    protected double calculateTotalDistance(
            List<Address> route,
            Map<Address, Integer> addressToIndex,
            long[][] distanceMatrix
    ) {
        double total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int fromIndex = addressToIndex.get(route.get(i));
            int toIndex = addressToIndex.get(route.get(i + 1));
            total += distanceMatrix[fromIndex][toIndex];
        }
        return total;
    }
}
