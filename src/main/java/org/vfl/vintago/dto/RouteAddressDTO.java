package org.vfl.vintago.dto;

public class RouteAddressDTO {
    private final int stepOrder;
    private final String status;
    private final AddressDTO address;

    public RouteAddressDTO(int stepOrder, String status, AddressDTO address) {
        this.stepOrder = stepOrder;
        this.status = status;
        this.address = address;
    }

    public int getStepOrder() {
        return stepOrder;
    }

    public String getStatus() {
        return status;
    }
    public AddressDTO getAddress() {
        return address;
    }
}
