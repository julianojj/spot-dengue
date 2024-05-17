package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class GetFile {
    private final FileRepository fileRepository;

    public GetFile(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public byte[] execute(String name) throws Exception {
        return this.fileRepository.Get(name);
    }
}
