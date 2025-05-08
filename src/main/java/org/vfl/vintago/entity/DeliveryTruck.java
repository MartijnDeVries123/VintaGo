package org.vfl.vintago.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "delivery_truck")
public class DeliveryTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "deliveryTruck", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Route> routes;

    public DeliveryTruck() {}

    public DeliveryTruck(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}