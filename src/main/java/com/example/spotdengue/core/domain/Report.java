package com.example.spotdengue.core.domain;

import com.example.spotdengue.core.exceptions.ValidationException;
import lombok.Getter;

import java.time.Instant;
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
    private String status;
    private String reason;
    private final List<String> images;
    private final Instant reportDate;
    private Instant updateDate;

    private Report(
            String id,
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments,
            String status,
            String reason,
            Instant reportDate,
            Instant updateDate
    ) {
        this.ID = id;
        this.mobilePhone = mobilePhone;
        this.geolocation = new Geolocation(latitude, longitude);
        this.address = address;
        this.comments = comments;
        this.images = new ArrayList<>();
        this.status = status;
        this.reason = reason;
        this.reportDate = reportDate;
        this.updateDate = updateDate;
    }

    public static Report of(
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments

    ) {
        Instant today = Instant.now();
        return new Report(
                UUID.randomUUID().toString(),
                mobilePhone,
                latitude,
                longitude,
                address,
                comments,
                "pending",
                "",
                today,
                today
        );
    }

    public static Report restore(
            String id,
            String mobilePhone,
            double latitude,
            double longitude,
            Address address,
            String comments,
            String status,
            String reason,
            Instant reportDate,
            Instant updateDate
    ) {
        return new Report(
                id,
                mobilePhone,
                latitude,
                longitude,
                address,
                comments,
                status,
                reason,
                reportDate,
                updateDate
        );
    }

    public void validate() throws ValidationException {
        if (this.mobilePhone.isEmpty()) throw new ValidationException("mobilePhone is required");
    }

    public void addImage(String imageURL) {
        images.add(imageURL);
    }

    public void resolveReport(String reason) throws ValidationException {
        if (reason.isEmpty()) throw new ValidationException("reason is required");
        this.reason = reason;
        this.updateDate = Instant.now();
        status = "resolved";
    }

    public void cancelReport(String reason) throws ValidationException {
        if (reason.isEmpty()) throw new ValidationException("reason is required");
        this.reason = reason;
        this.updateDate = Instant.now();
        status = "canceled";
    }
}
