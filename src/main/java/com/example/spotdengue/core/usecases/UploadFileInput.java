package com.example.spotdengue.core.usecases;

import java.io.InputStream;

public record UploadFileInput(String name, String type, InputStream body) { }
