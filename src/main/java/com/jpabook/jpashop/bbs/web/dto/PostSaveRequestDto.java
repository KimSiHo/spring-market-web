package com.jpabook.jpashop.bbs.web.dto;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.bbs.domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
public class PostSaveRequestDto {
    private String title;
    private String content;
    private Account writer;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }

}
