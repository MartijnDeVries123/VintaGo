package org.vfl.vintago.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoUtilsTest {

    // Base address coordinates are Campus 2, 8017 CA Zwolle
    final double WINDESHEIM_DEPOT_LAT = 52.4964;
    final double WINDESHEIM_DEPOT_LNG = 6.0846;

    @Test
    void testHaversine_sameLocation() {
        // Same location, expecting zero distance
        long result = GeoUtils.haversine(WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG, WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG);

        assertEquals(0, result, "Distance between the same locations should be zero.");
    }

    @Test
    void testHaversine_shortDistance() {
        // Short distance between two locations
        double lat2 = 52.5500;
        double lng2 = 5.9170; // Nearby location Kampen

        long result = GeoUtils.haversine(WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG, lat2, lng2);

        assertEquals(12, result, "Distance should be approximately 12 km.");
    }

    @Test
    void testHaversine_mediumDistance() {
        // Medium distance between two locations
        double lat2 = 48.8566;
        double lng2 = 2.3522; // Paris, France

        long result = GeoUtils.haversine(WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG, lat2, lng2);

        assertEquals(482, result, "Distance between Windesheim and Paris should be approximately 482 km.");
    }

    @Test
    void testHaversine_longDistance() {
        // Long distance between two locations
        double lat2 = 40.7128;
        double lng2 = -74.0060; // New York, USA

        long result = GeoUtils.haversine(WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG, lat2, lng2);

        assertEquals(5932, result, "Distance between Windesheim and New York should be approximately 5,932 km.");
    }

    @Test
    void testHaversine_veryLongDistance() {
        // Very long distance between two globally opposite locations
        double lat2 = -33.8678;
        double lng2 = 151.2073; // Sydney, Australia

        long result = GeoUtils.haversine(WINDESHEIM_DEPOT_LAT, WINDESHEIM_DEPOT_LNG, lat2, lng2);

        assertEquals(16563, result, "Distance between Windesheim and Sydney should be approximately 16,563 km.");
    }
}