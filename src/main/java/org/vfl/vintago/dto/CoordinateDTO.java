package org.vfl.vintago.dto;

public class CoordinateDTO {
    private double lat;
    private double lng;

    public CoordinateDTO(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String toString() {
        return "latitude " + lat + ", longitude " + lng;
    }
}
