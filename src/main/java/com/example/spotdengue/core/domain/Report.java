package com.example.spotdengue.core.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Report {
    private final String ID;
    private final String mobilePhone;
    private final Geolocation geolocation;
    private final Address address;
    private final String comments;
    private final List<String> images;
    private final LocalDate reportDate;
    private String status;

    private Report(
            String id,
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments,
            String status,
            LocalDate reportDate
    ) {
        this.ID = id;
        this.mobilePhone = mobilePhone;
        this.geolocation = new Geolocation(latitude, longitude);
        this.address = address;
        this.comments = comments;
        this.images = new ArrayList<>();
        this.status = status;
        this.reportDate = reportDate;
    }

    public static Report of(
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments

    ) {
        return new Report(
                UUID.randomUUID().toString(),
                mobilePhone,
                latitude,
                longitude,
                address,
                comments,
                "pending",
                LocalDate.now());
    }

    public static Report restore(
            String id,
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments,
            String status,
            LocalDate reportDate
    ) {
        return new Report(
                id,
                mobilePhone,
                latitude,
                longitude,
                address,
                comments,
                status,
                reportDate);
    }

    public void addImage(String imageURL) {
        images.add(imageURL);
    }

    public void resolveReport() {
        status = "resolved";
    }

    public void cancelReport() {
        status = "canceled";
    }
}
