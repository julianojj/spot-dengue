package com.example.spotdengue.core.domain;

import lombok.Getter;

@Getter
public class Address {
    private final String city;
    private final String zipCode;
    private final String state;
    private final String street;
    private final Integer streetNumber;
    private final String neighborhood;

    public Address(String city, String zipCode, String state, String street, Integer streetNumber, String neighborhood) {
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.street = street;
        this.streetNumber = streetNumber;
        this.neighborhood = neighborhood;
    }
}
