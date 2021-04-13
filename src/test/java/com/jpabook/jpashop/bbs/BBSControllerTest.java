package com.jpabook.jpashop.bbs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpabook.jpashop.WithAccount;
import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.domain.AccountRepository;
import com.jpabook.jpashop.bbs.domain.Post;
import com.jpabook.jpashop.bbs.domain.PostRepository;
import com.jpabook.jpashop.bbs.web.dto.PostSaveRequestDto;
import com.jpabook.jpashop.bbs.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@TestPropertySource("classpath:/test.properties")
@ActiveProfiles("local")
@SpringBootTest
class BBSControllerTest {
    /*
    테스트 순서
    등록 > 수정 > 삭제 > 불러오기 > JPA BaseEntity 정상 동작
    */

    @Autowired private PostRepository postRepository;

    @Autowired private AccountRepository accountRepository;

    @Autowired private MockMvc mvc;

    @DisplayName("게시판 - 등록")
    @WithAccount("siho")
    @Test
    public void 게시물_등록() throws Exception {
        //given
        Account writer = accountRepository.findByNickname("siho");
        String title = "title";
        String content = "content";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        //when
        mvc.perform(post("/bbs/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("게시판 - 수정")
    @WithAccount("siho")
    @Test
    public void 게시물_수정() throws Exception {
        //given
        Account writer = accountRepository.findByNickname("siho");
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .build());

        Long updateId = savedPost.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        //when
        mvc.perform(put("/bbs/update/" + updateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
                .with(csrf()))
                .andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @DisplayName("게시판 - 불러오기")
    @WithAccount("siho")
    @Test
    void 게시물_불러오기() {
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

    @DisplayName("게시판 - JPA BaseEntity")
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
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}