package com.example.spotdengue.core.usecases;

import java.util.List;

public record GetReportsOutput(String reportID, String status, String comments, List<String> images){ }
