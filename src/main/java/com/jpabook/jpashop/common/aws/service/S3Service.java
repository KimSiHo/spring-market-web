package com.jpabook.jpashop.common.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jpabook.jpashop.common.aws.dto.S3Component;
import com.jpabook.jpashop.common.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service implements FileUploadService {

    private final AmazonS3Client amazonS3Client;
    private final S3Component s3Component;

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(s3Component.getBucket(), fileName, inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getFileUrl(String fileName) {
        return String.valueOf(amazonS3Client.getUrl(s3Component.getBucket(), fileName));
    }
}
