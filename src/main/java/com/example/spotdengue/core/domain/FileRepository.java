package com.example.spotdengue.core.domain;


import java.io.InputStream;

public interface FileRepository {
    void Upload(File file);
    void Delete(String name) throws Exception;
    byte[] Get(String name) throws Exception;
}
