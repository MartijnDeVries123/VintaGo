package org.vfl.vintago.dto;

import java.time.LocalDate;
import java.util.List;

public class RouteDTO {
    private final Long id;
    private final LocalDate deliveryDate;
    private final String deliveryTruckName;
    private final List<RouteAddressDTO> addresses;

    public RouteDTO(Long id, LocalDate deliveryDate, String deliveryTruckName, List<RouteAddressDTO> addresses) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.deliveryTruckName = deliveryTruckName;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }
    public String getDeliveryTruckName() {
        return deliveryTruckName;
    }

    public List<RouteAddressDTO> getAddresses() {
        return addresses;
    }
}