package org.vfl.vintago.service;

import org.junit.jupiter.api.Test;
import org.vfl.vintago.entity.Address;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClusterOrdersServiceTests {

    @Test
    public void testClusterOrders_ValidInput() {
        // Arrange
        final int NUMBER_OF_ADDRESSES = 10;
        final int CLUSTER_SIZE = 3;
        ClusterOrdersService service = new ClusterOrdersService();
        service.setClusterSize(CLUSTER_SIZE);

        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_ADDRESSES; i++) {
            Address address = new Address();

            // Base address coordinates are Campus 2, 8017 CA Zwolle
            address.setLat(i + 52.5175977);
            address.setLng(i + 6.0833214);
            addresses.add(address);
        }

        // Act
        List<List<Address>> clusters = service.clusterOrders(addresses);

        // Assert
        assertEquals(CLUSTER_SIZE, clusters.size(), "Cluster size should match the set cluster size.");
        assertTrue(clusters.stream().flatMap(List::stream).count() == NUMBER_OF_ADDRESSES, "Total addresses should match the input size.");
    }

    @Test
    public void testClusterOrders_InvalidInput_ThrowsException() {
        // Arrange
        ClusterOrdersService service = new ClusterOrdersService();
        service.setClusterSize(0); // Invalid cluster size

        List<Address> addresses = new ArrayList<>(); // Empty input

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.clusterOrders(addresses), "Should throw exception for invalid input.");
    }
}
