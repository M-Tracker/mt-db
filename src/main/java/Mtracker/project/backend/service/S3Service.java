package Mtracker.project.backend.service;

import Mtracker.project.backend.aws.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {


    @Autowired
    private AwsConfig awsConfig;

    @Value("${aws.s3.imagebucket}")
    private String bucketName;

    public void uploadFile(String key, MultipartFile file) throws IOException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            awsConfig.s3Client().putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            System.out.println("File uploaded successfully!");

        } catch (S3Exception e) {
            throw new IOException("Failed to upload file to S3", e);
        }
    }

    public void updateFile(String key, MultipartFile file) throws IOException {
        // For updating, we can use the same method as uploading since it will overwrite the existing file
        uploadFile(key, file);
    }

    public InputStreamResource downloadFile(String key, String bucketName) {
        // Download file from S3
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return new InputStreamResource(awsConfig.s3Client().getObject(getObjectRequest));
    }

    public void deleteFile(String key) throws IOException {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            awsConfig.s3Client().deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully!");

        } catch (S3Exception e) {
            throw new IOException("Failed to delete file from S3", e);
        }
    }
}
