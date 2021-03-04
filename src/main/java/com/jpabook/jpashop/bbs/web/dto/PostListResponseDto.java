package com.jpabook.jpashop.bbs.web.dto;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.bbs.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private Account writer;
    private LocalDateTime modifiedDate;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.modifiedDate = post.getModifiedDate();
    }
}
