package com.example.spotdengue.core.domain;


import java.io.InputStream;

public interface FileRepository {
    void Upload(File file);
    byte[] Get(String name) throws Exception;
}
