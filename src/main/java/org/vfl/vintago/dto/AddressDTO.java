package org.vfl.vintago.dto;

public class AddressDTO {
    private final Long id;
    private final String street;
    private final String number;
    private final String city;
    private final String zip;
    private final CoordinateDTO location;
    private final String status;

    public AddressDTO(Long id, String street, String number, String city, String zip, CoordinateDTO location, String status) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.city = city;
        this.zip = zip;
        this.location = location;
        this.status = status;
    }
    public Long getId() {
        return id;
    }
    public String getStreet() {
        return street;
    }
    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }
    public String getZip() {
        return zip;
    }
    public CoordinateDTO getLocation() {
        return location;
    }
    public String getStatus() {
        return status;
    }
}
