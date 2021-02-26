package com.jpabook.jpashop.bbs.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostUpdateRequestDto {
    private String title;
    private String content;
}
