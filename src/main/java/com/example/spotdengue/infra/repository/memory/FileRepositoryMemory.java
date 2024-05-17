package com.example.spotdengue.infra.repository.memory;

import com.example.spotdengue.core.domain.File;
import com.example.spotdengue.core.domain.FileRepository;
import com.example.spotdengue.core.exceptions.NotFoundException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileRepositoryMemory implements FileRepository {
    private final List<File> files;

    public FileRepositoryMemory() {
        this.files = new ArrayList<>();
    }

    @Override
    public void Upload(File file) {
        this.files.add(file);
    }

    @Override
    public byte[] Get(String name) throws Exception {
        for (File file: this.files) {
            if (Objects.equals(file.getName(), name)) {
                return file.getBody();
            }
        }
        throw new NotFoundException("file not found");
    }
}
