package org.vfl.vintago.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @ManyToOne
    @JoinColumn(name = "delivery_truck_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_route_truck"))
    private DeliveryTruck deliveryTruck;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RouteAddress> routeAddresses;

    // Constructors
    public Route() {}

    public Route(String name, LocalDate deliveryDate, DeliveryTruck deliveryTruck) {
        this.name = name;
        this.deliveryDate = deliveryDate;
        this.deliveryTruck = deliveryTruck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public DeliveryTruck getDeliveryTruck() {
        return deliveryTruck;
    }

    public void setDeliveryTruck(DeliveryTruck deliveryTruck) {
        this.deliveryTruck = deliveryTruck;
    }

    public void setRouteAddresses(List<RouteAddress> routeAddresses) {
        this.routeAddresses = routeAddresses;
    }

    public List<RouteAddress> getRouteAddresses() {
        return routeAddresses;
    }
}