package org.vfl.vintago.dto;

public class DeliveryTruckDTO {
    private final int id;
    private final String name;

    public DeliveryTruckDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
