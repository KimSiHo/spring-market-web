package com.jpabook.jpashop.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadResult {
    private String path;
    private String name;
    private Long size;
}
