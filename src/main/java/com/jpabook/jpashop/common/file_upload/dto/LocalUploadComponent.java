package com.jpabook.jpashop.common.file_upload.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "local.filesystem")
@Component
public class LocalUploadComponent {

    private String uploadPath;
}
