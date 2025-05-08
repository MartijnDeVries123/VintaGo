package org.vfl.vintago.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "route_address")
public class RouteAddress {

    @EmbeddedId
    private RouteAddressId id;

    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    public RouteAddress() {}

    public RouteAddress(Route route, Address address, int stepOrder) {
        this.route = route;
        this.address = address;
        this.stepOrder = stepOrder;
        this.id = new RouteAddressId(route.getId(), address.getId());
    }

    public RouteAddressId getId() {
        return id;
    }

    public void setId(RouteAddressId id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.stepOrder = stepOrder;
    }
}