package org.vfl.vintago.util;

import org.vfl.vintago.entity.Address;

import java.util.List;

public class DistanceMatrix {
    public static long[][] getDistanceMatrix(List<Address> addressList) {
        int n = addressList.size();
        long[][]  distanceMatrix = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    distanceMatrix[i][j] = GeoUtils.haversine(
                            addressList.get(i).getLat(),
                            addressList.get(i).getLng(),
                            addressList.get(j).getLat(),
                            addressList.get(j).getLng()
                    );
                }
            }
        }
        return distanceMatrix;
    }
}
