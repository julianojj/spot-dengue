package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.File;
import com.example.spotdengue.core.domain.FileRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UploadFile {
    private final FileRepository fileRepository;

    public UploadFile(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public UploadFileOutput execute(UploadFileInput input) throws Exception {
        try (InputStream body = input.body()) {
            File file = new File(input.name(), input.type(), body.readAllBytes());
            this.fileRepository.Upload(file);
            String path = "http://localhost:8080/get_file/" + file.getName();
            return new UploadFileOutput(
                    file.getName(),
                    path
            );
        }
    }
}
