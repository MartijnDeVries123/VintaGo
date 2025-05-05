package org.vfl.vintago.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "delivery_truck")
public class DeliveryTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name= "name", nullable = false)
    private String name;

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