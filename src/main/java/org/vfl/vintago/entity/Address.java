package org.vfl.vintago.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.vfl.vintago.dto.CoordinateDTO;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "zip")
    private String zip;

    @Column(name = "city")
    private String city;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Transient
    @JsonProperty
    public CoordinateDTO getLocation() {
        return new CoordinateDTO(lat, lng);
    }

    @JsonProperty("location")
    public void setLocation(CoordinateDTO location) {
        if (location != null) {
            this.lat = location.getLat();
            this.lng = location.getLng();
        }
    }

    @Override
    public String toString() {
        String address = "Address{" +
                "street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                "}";
        if (this.getLocation() == null) {
            return address;
        } else {
            address += "," + getLocation().toString();
            return address;
        }
    }
}
