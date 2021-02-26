package com.jpabook.jpashop.common.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.domain.ProductImageFile;
import com.jpabook.jpashop.common.domain.ProductImageFileRepository;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.web.ProductUploadForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final ProductRepository productRepository;
    private final ProductImageFileRepository productImageFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile multipartFile, Account account, ProductUploadForm productUploadForm) throws IOException {
        File convertedFile = convert(multipartFile);

        String imgURL = uploadS3(convertedFile, "static");

        Product product = Product.builder()
                .pio(productUploadForm.getPio())
                .account(account)
                .fileName(multipartFile.getOriginalFilename())
                .build();

        ProductImageFile productImageFile = ProductImageFile.builder()
                                                            .product(product)
                                                            .imageURL(imgURL)
                                                            .product(product)
                                                            .build();
        productRepository.save(product);
        productImageFileRepository.save(productImageFile);

        return "success";
    }

    private String uploadS3(File uploadFile, String dirName) {
        String fullPath = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fullPath);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fullPath) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fullPath, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fullPath).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("============" + targetFile.getAbsolutePath());
            log.info("파일이 삭제되었습니다.");
        }
        else { log.info("파일이 삭제되지 못했습니다: {}" , targetFile.getName()); }
    }

    private File convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return convertFile;
        }
        throw new IllegalArgumentException(String.format("파일 변환이 실패했습니다. 파일 이름: %s", file.getName()));
    }

}