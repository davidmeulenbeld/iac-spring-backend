package com.iacbackend.shop.model;

import javax.persistence.*;

@Entity
public class Addres {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
