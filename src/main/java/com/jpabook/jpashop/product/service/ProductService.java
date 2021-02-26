package com.jpabook.jpashop.product.service;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.dto.UploadResult;
import com.jpabook.jpashop.product.domain.Product;
import com.jpabook.jpashop.product.domain.ProductRepository;
import com.jpabook.jpashop.product.web.ProductUploadForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${upload.path}")
    String uploadPath;

    public void upload(MultipartFile file, Account account, ProductUploadForm productUploadForm) {

        UploadResult fileUploadResult = saveFile(file);

        Product product = Product.builder()
                            .pio(productUploadForm.getPio())
                            .account(account)
                            .fileName(file.getOriginalFilename())
                            .build();

        productRepository.save(product);
    }

    private UploadResult saveFile(MultipartFile file) {
        String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String id = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (StringUtils.isNotEmpty(extension)) {
            id = id + "." + extension;
        }

        File save = new File(String.format("%s%s%s%s%s", uploadPath, File.separator, path, File.separator, id));
        System.out.println("save = " + save.getAbsolutePath());

        try {
            FileUtils.forceMkdirParent(save);
            file.transferTo(save);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 이미지를 저장한 후, 리사이즈 작업을 실행한다.
        try {
            resize(extension, save);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return UploadResult.builder()
                .path(String.format("%s%s%s", path, File.separator, id))
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .build();
    }

    private void resize(String extension, File savedFile) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(savedFile);
        BufferedImage read = ImageIO.read(fileInputStream);

        BufferedImage bufferedImage = new BufferedImage(300,300, read.getType());

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(read, 0, 0, 300,300,null);
        graphics.dispose();

        File absoluteFile = savedFile.getAbsoluteFile();

        ImageIO.write(bufferedImage, extension, absoluteFile);
    }



}
