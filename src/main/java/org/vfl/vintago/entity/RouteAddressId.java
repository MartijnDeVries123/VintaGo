package org.vfl.vintago.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RouteAddressId implements Serializable {

    private int routeId;
    private int addressId;

    public RouteAddressId() {}

    public RouteAddressId(int routeId, int addressId) {
        this.routeId = routeId;
        this.addressId = addressId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteAddressId)) return false;
        RouteAddressId that = (RouteAddressId) o;
        return Objects.equals(routeId, that.routeId) &&
                Objects.equals(addressId, that.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, addressId);
    }
}