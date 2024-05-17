package com.example.spotdengue.core.domain;

import com.example.spotdengue.core.exceptions.ValidationException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class File {
    private final List<String> ACCEPTED_TYPES = Arrays.asList("image/jpeg", "image/png");
    private final String name;
    private final String type;
    private final byte[] body;

    public File(
            String name,
            String type,
            byte[] body
    ) throws Exception {
        this.name = this.generateFileName(name);
        this.type = type;
        this.body = body;
        this.validate();
    }

    private void validate() throws Exception {
        if (!this.isValidType()) throw new ValidationException("unsupported file type");
    }

    private boolean isValidType() {
        return this.ACCEPTED_TYPES.contains(this.type);
    }

    private String generateFileName(String name) {
        String ext = this.getFileExtension(name);
        return UUID.randomUUID() + "." + ext;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
