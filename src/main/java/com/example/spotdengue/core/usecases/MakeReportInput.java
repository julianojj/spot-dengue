package com.example.spotdengue.core.usecases;

import java.util.List;

public record MakeReportInput(String mobilePhone, double latitude, double longitude, List<String> images, String comments) {}
