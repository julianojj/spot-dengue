package com.example.spotdengue.core.usecases;

import com.example.spotdengue.core.domain.FileRepository;

public class DeleteFile {
    private final FileRepository fileRepository;

    public DeleteFile(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void Execute(String fileID) throws Exception {
        this.fileRepository.Delete(fileID);
    }
}
