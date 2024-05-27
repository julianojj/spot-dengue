package com.example.spotdengue.core.usecases;

import java.time.Instant;
import java.util.List;

public record GetReportsOutput(
        String reportID,
        Double latitude,
        Double longitude,
        String city,
        String zipCode,
        String state,
        String street,
        Integer streetNumber,
        String neighborhood,
        String status,
        String comments,
        Instant reportDate,
        List<String> images
){ }
