package org.vfl.algorithms;

import org.junit.jupiter.api.Test;
import org.vfl.vintago.entity.Address;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinKernighanAlgorithmTest {

    @Test
    void solveForOneRoute_returnsOptimizedRouteOfCorrectLength() {
        Address a1 = createAddress(52.379189, 4.899431);
        Address a2 = createAddress(51.9225, 4.47917);
        Address a3 = createAddress(52.0705, 4.3007);
        Address a4 = createAddress(52.0907, 5.1214);
        Address a5 = createAddress(51.4416, 5.4697);
        Address a6 = createAddress(53.2194, 6.5665);
        Address a7 = createAddress(52.5200, 5.7480);
        Address a8 = createAddress(51.5866, 4.7750);
        Address a9 = createAddress(52.5050, 6.0900);
        Address a10 = createAddress(51.8126, 5.8372);

        List<Address> orders = List.of(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10);

        LinKernighanAlgorithm algorithm = new LinKernighanAlgorithm();
        List<Address> result = algorithm.solveForOneRoute(orders);

        assertNotNull(result);
        assertEquals(10, result.size(), "De lengte van de geoptimaliseerde route moet gelijk zijn aan het aantal inputadressen.");
        assertTrue(result.containsAll(orders), "Alle originele adressen moeten nog steeds in de output zitten.");
    }

    private Address createAddress(double lat, double lng) {
        Address address = new Address();
        address.setLat(lat);
        address.setLng(lng);
        return address;
    }
}
