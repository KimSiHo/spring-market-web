package com.jpabook.jpashop.bbs.domain;

import com.jpabook.jpashop.WithAccount;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.domain.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryTest {

    @LocalServerPort private int port;
    @Autowired PostRepository postRepository;
    @Autowired AccountRepository accountRepository;

    @Autowired DataSource dataSource;

    @AfterEach
    void cleanUp() {
        postRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    void 테스트(){
        System.out.println("port = " + port);
    }

    @WithAccount("siho")
    @Rollback(false)
    @Test
    void 게시글저장_불러오기() {
        //given
        Account writer = accountRepository.findByNickname("siho");
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getWriter().getNickname()).isEqualTo("siho");
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now();
        Thread.sleep(100);
        postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .build());
        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);

        System.out.println(">>>>>>>>> createDate=" + post.getCreatedDate() + ", modifiedDate=" + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }

}