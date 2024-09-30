package com.songspasssta.ploggingservice.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.credentials.bucket}")
    private String bucketName;

    public String upload(final MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return null;
        }

        final String originalFileName = multipartFile.getOriginalFilename();
        final String uploadFileName = getUuidFileName(originalFileName);

        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        final InputStream inputStream = multipartFile.getInputStream();
        final String keyName = "plogging/" + uploadFileName;

        amazonS3Client.putObject(
                new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucketName, keyName).toString();
    }

    private String getUuidFileName(String fileName) {
        final String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }
}
