package org.vfl.vintago.dto;

public class RouteAddressDTO {
    private final int stepOrder;
    private final AddressDTO address;

    public RouteAddressDTO(int stepOrder, AddressDTO address) {
        this.stepOrder = stepOrder;
        this.address = address;
    }

    public int getStepOrder() {
        return stepOrder;
    }
    public AddressDTO getAddress() {
        return address;
    }
}
