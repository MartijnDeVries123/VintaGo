package org.vfl.vintago.util;

import org.junit.jupiter.api.Test;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.entity.RouteAddress;
import org.vfl.vintago.entity.RouteAddressId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class RouteDistanceCalculatorTest {

    @Test
    void testCalculateTotalRouteDistanceWithTenAddresses() {
        Route route = new Route();
        List<Address> addresses = new ArrayList<>();

        addresses.add(createAddress(52.379189, 4.899431));
        addresses.add(createAddress(51.9225, 4.47917));
        addresses.add(createAddress(52.0705, 4.3007));
        addresses.add(createAddress(52.0907, 5.1214));
        addresses.add(createAddress(51.4416, 5.4697));
        addresses.add(createAddress(53.2194, 6.5665));
        addresses.add(createAddress(52.5200, 5.7480));
        addresses.add(createAddress(51.5866, 4.7750));
        addresses.add(createAddress(52.5050, 6.0900));
        addresses.add(createAddress(51.8126, 5.8372));

        List<RouteAddress> routeAddresses = new ArrayList<>();
        int step = 1;
        for (Address addr : addresses) {
            RouteAddress ra = new RouteAddress();
            ra.setRoute(route);
            ra.setAddress(addr);
            ra.setStepOrder(step++);

            routeAddresses.add(ra);
        }

        route.setRouteAddresses(routeAddresses);

        long total = RouteDistanceCalculator.calculateTotalRouteDistance(route);

        long expectedTotal = 1011;
        assertEquals(expectedTotal, total, "De berekende afstand moet exact overeenkomen met de verwachte waarde");
    }

    private Address createAddress(double lat, double lng) {
        Address address = new Address();
        address.setLat(lat);
        address.setLng(lng);
        return address;
    }
}