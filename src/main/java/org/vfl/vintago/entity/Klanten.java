package org.vfl.vintago.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "klanten")
public class Klanten {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "naam", nullable = false, length = 45)
    private String naam;

    @Column(name = "straat", nullable = false, length = 100)
    private String straat;

    @Column(name = "plaats", nullable = false, length = 45)
    private String plaats;

    @Column(name = "postcode", nullable = false, length = 10)
    private String postcode;

    @Column(name = "huisnummer", nullable = false, length = 10)
    private String huisnummer;

    @Column(name = "telefoonnummer", length = 16)
    private String telefoonnummer;

    @Column(name = "email", length = 100)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}