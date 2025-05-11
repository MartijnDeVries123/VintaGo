package org.vfl.vintago.util;

import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.entity.RouteAddress;

import java.util.ArrayList;
import java.util.List;

public class RouteDistanceCalculator {
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

    // Bereken de totale afstand van de route
    public static long calculateTotalRouteDistance(Route route) {
        long totalDistance = 0;

        List<Address> addresses = route.getRouteAddresses().stream().map(RouteAddress::getAddress).toList();

        List<Address> withDepot = new ArrayList<>();

        withDepot.add(WINDESHEIM_DEPOT);
        withDepot.addAll(addresses);
        withDepot.add(WINDESHEIM_DEPOT);

        for (int i = 0; i < withDepot.size() - 1; i++) {
            Address from = withDepot.get(i);
            Address to = withDepot.get(i + 1);

            double distance = GeoUtils.haversine(
                    from.getLat(), from.getLng(),
                    to.getLat(), to.getLng()
            );
            totalDistance += distance;
        }
        return totalDistance;
    }
}