package org.vfl.vintago.dto;

import org.springframework.web.reactive.function.client.WebClient;
import org.vfl.vintago.service.GeocodingService;

public class AddressDTO {
    private String street;
    private String number;
    private String postal_code;
    private String city;
    private CoordinateDTO location;

    public AddressDTO(String street, String number, String postal_code, String city) {
        this.street = street;
        this.number = number;
        this.postal_code = postal_code;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public String getCity() {
        return city;
    }

    public CoordinateDTO getLocation() {
        return location;
    }

    public void setLocation() {
        String preparedAddress = street + "," + number + "," + city;
        GeocodingService geocodingService = new GeocodingService(WebClient.builder());
        this.location = geocodingService.getCoordinates(preparedAddress).block();
    }

    @Override
    public String toString() {
        String address = "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", city='" + city + '\'' +
                "}";
        if (location == null) {
            return address;
        } else {
            address += "," + location.toString();
            return address;
        }
    }
}
