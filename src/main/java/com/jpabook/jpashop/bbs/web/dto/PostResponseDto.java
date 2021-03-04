package com.jpabook.jpashop.bbs.web.dto;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.bbs.domain.Post;
import lombok.Data;

@Data
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private Account writer;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
    }
}
