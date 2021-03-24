package com.jpabook.jpashop.common.file.service;

import com.jpabook.jpashop.common.file.dto.LocalUploadComponent;
import com.jpabook.jpashop.common.file.dto.UploadInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Profile("fs-local")
@RequiredArgsConstructor
@Service
public class localFileUploadService implements FileUploadService {

    private final FileService fileService;
    private final LocalUploadComponent localUploadComponent;

    private UploadInfo saveFile(MultipartFile file) {

        String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String id = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (StringUtils.isNotEmpty(extension)) {
            id = id + "." + extension;
        }

        File save = new File(String.format("%s%s%s%s%s", localUploadComponent.getUploadPath(), File.separator, path, File.separator, id));
        //System.out.println("save = " + save.getAbsolutePath());

        try {
            FileUtils.forceMkdirParent(save);
            file.transferTo(save);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 이미지를 저장한 후, 리사이즈 작업을 실행한다.
        try {
            fileService.resize(extension, save);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return UploadInfo.builder()
                .path(String.format("%s%s%s", path, File.separator, id))
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .build();
    }
}
