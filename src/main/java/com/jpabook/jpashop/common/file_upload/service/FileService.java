package com.jpabook.jpashop.common.file_upload.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jpabook.jpashop.common.aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    // 업로드 위임 서비스
    private final S3Service s3Service;

    public String uploadImage(MultipartFile file) {
        String fileName = createFileName(file.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("파일 > S3 업로드 작업 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename()));
        }
        return s3Service.getFileUrl(fileName);
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

    public void resize(String extension, File savedFile) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(savedFile);
        BufferedImage read = ImageIO.read(fileInputStream);

        BufferedImage bufferedImage = new BufferedImage(300,300, read.getType());

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.drawImage(read, 0, 0, 300,300,null);
        graphics.dispose();

        File absoluteFile = savedFile.getAbsoluteFile();

        ImageIO.write(bufferedImage, extension, absoluteFile);
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

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("============" + targetFile.getAbsolutePath());
            log.info("파일이 삭제되었습니다.");
        }
        else { log.info("파일이 삭제되지 못했습니다: {}" , targetFile.getName()); }
    }

   /* public byte[] bytes(String path, String id) throws IOException {
        return Files.readAllBytes(
                new File(String.format(
                        "%s%s%s%s%s",
                        uploadPath, File.separator,
                        path, File.separator, id)).toPath()
        );
    }

    public void to(String path, String id, OutputStream out) throws IOException {
        IOUtils.copy(new ByteArrayInputStream(bytes(path, id)), out);
    }*/

}
