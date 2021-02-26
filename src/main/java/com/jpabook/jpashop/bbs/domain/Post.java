package com.jpabook.jpashop.bbs.domain;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor @Builder
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private Account writer;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
