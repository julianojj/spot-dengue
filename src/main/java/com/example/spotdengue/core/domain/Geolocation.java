package com.example.spotdengue.core.domain;

import lombok.Getter;

@Getter
public class Geolocation {
    private final double latitude;
    private final double longitude;

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
