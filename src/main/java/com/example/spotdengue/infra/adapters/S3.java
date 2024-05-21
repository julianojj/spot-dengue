package com.example.spotdengue.infra.adapters;

import com.example.spotdengue.core.domain.File;
import com.example.spotdengue.core.domain.FileRepository;
import com.example.spotdengue.core.exceptions.NotFoundException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

public class S3 implements FileRepository {
    private final S3Client s3Client;

    public S3() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create("Test", "Test");
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        this.s3Client = S3Client.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(credentialsProvider)
                .endpointOverride(URI.create("http://localhost:4566"))
                .forcePathStyle(true)
                .build();
    }

    @Override
    public void Upload(File file) {
        PutObjectRequest input = PutObjectRequest.builder()
                .bucket("report")
                .key(file.getName())
                .build();
        this.s3Client.putObject(input, RequestBody.fromBytes(file.getBody()));
    }

    @Override
    public void Delete(String name) throws Exception {
        DeleteObjectRequest input = DeleteObjectRequest.builder()
                .bucket("report")
                .key(name)
                .build();
        this.s3Client.deleteObject(input);
    }

    @Override
    public byte[] Get(String name) throws Exception {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket("report")
                    .key(name)
                    .build();
            return this.s3Client.getObject(request).readAllBytes();
        } catch (NoSuchKeyException err) {
            throw new NotFoundException("file not found");
        }
    }
}
