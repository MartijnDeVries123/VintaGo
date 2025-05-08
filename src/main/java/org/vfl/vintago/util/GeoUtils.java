package org.vfl.vintago.util;

public class GeoUtils {
    private static final double EARTH_RADIUS = 6371.0; // mean Earth radius in km

    /*
    * Adapted from: https://rosettacode.org/wiki/Haversine_formula
    * Original content licensed under GNU Free Documentation License 1.2
    * This adaptation is used in accordance with that license.
    * For more: https://www.gnu.org/licenses/fdl-1.2.html
    */
    public static double haversine(double lat1, double lng1, double lat2, double lng2) { // shortest path over the Earth’s surface

        // convert degrees to radians for Math calculations
        double rLat1 = Math.toRadians(lat1);
        double rLat2 = Math.toRadians(lat2);
        double dLat = rLat2 - rLat1;
        double dLng = Math.toRadians(lng2 - lng1);

        // calculates the square of half the chord length between two points on a sphere
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.cos(rLat1) * Math.cos(rLat2) *
                    Math.sin(dLng / 2) * Math.sin(dLng / 2);

        // calculates the central angle between the two points on a sphere
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // returns the actual distance in km
        return EARTH_RADIUS * c;
    }
}
