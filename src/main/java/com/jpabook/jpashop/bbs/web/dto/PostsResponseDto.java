package com.jpabook.jpashop.bbs.web.dto;

import com.jpabook.jpashop.bbs.domain.posts.Posts;
import lombok.Data;

@Data
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
