package com.jpabook.jpashop.common.file_upload.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadInfo {
    private String path;
    private String name;
    private Long size;
}
