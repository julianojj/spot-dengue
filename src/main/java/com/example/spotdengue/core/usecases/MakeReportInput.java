package com.example.spotdengue.core.usecases;

import java.util.List;

public record MakeReportInput(
        String mobilePhone,
        double latitude,
        double longitude,
        String city,
        String zipCode,
        String state,
        String street,
        Integer streetNumber,
        String neighborhood,
        List<String> images,
        String comments
) {}
