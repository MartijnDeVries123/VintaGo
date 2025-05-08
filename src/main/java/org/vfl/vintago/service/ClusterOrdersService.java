package org.vfl.vintago.service;

import org.springframework.stereotype.Service;
import org.vfl.vintago.dto.CoordinateDTO;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.util.GeoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ClusterOrdersService {
    private int clusterSize;
    private static final double CENTROID_THRESHOLD = 0.0001; // Accuracy of 0.0001 is approx. 10 meter in latitude and longitude

    public void setClusterSize(int size) {
        this.clusterSize = size;
    }

    private static double distanceTo(CoordinateDTO coordinate1, CoordinateDTO coordinate2) {
        return GeoUtils.haversine(coordinate1.getLat(),coordinate1.getLng(),coordinate2.getLat(),coordinate2.getLng());
    }

    public List<List<Address>> clusterOrders(List<Address> addresses) {
        if (addresses == null || addresses.isEmpty() || clusterSize < 2 || addresses.size() < clusterSize) {
            throw new IllegalArgumentException("Invalid input: No or not enough addresses for clustering");
        }

        // Centroids are randomly selected unique addresses from the input list
        List<Address> centroids = new ArrayList<>();
        List<Address> remainingAddresses = new ArrayList<>(addresses);
        Random random = new Random();

        for (int i = 0; i < clusterSize; i++) {
            int randomIndex = random.nextInt(remainingAddresses.size());
            centroids.add(remainingAddresses.get(randomIndex));
            remainingAddresses.remove(randomIndex);
        }

        // Initialize the clusters
        List<List<Address>> clusters = new ArrayList<>();
        for (int i = 0; i < clusterSize; i++) {
            clusters.add(new ArrayList<>());
        }

        // Assign each address to the closest centroid, recalculate centroids and repeat the process until nothing changes
        boolean changed;
        do {
            // Empty the clusters for each iteration
            for (List<Address> cluster : clusters) {
                cluster.clear();
            }

            // Assign each address to the closest centroid
            for (Address address : addresses) {
                int closestClusterIndex = 0;
                double minDistance = distanceTo(centroids.get(0).getLocation(), address.getLocation());

                for (int i = 1; i < centroids.size(); i++) {
                    double distance = distanceTo(centroids.get(i).getLocation(), address.getLocation());
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestClusterIndex = i;
                    }
                }

                clusters.get(closestClusterIndex).add(address);
            }

            changed = false;
            // Recalculate centroids
            for (int i = 0; i < clusterSize; i++) {
                List<Address> cluster = clusters.get(i);
                if (cluster.isEmpty()) continue;

                double avgLat = 0.0;
                double avgLng = 0.0;
                for (Address address : cluster) {
                    avgLat += address.getLat();
                    avgLng += address.getLng();
                }

                avgLat /= cluster.size();
                avgLng /= cluster.size();

                Address newCentroid = new Address();
                newCentroid.setLat(avgLat);
                newCentroid.setLng(avgLng);

                if ((distanceTo(newCentroid.getLocation(), centroids.get(i).getLocation()) > CENTROID_THRESHOLD)) {
                    centroids.set(i, newCentroid);
                    changed = true;
                }
            }
        } while (changed);

        return clusters;
    }
}
