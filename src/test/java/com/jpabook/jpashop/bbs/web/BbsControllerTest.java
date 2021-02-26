package com.jpabook.jpashop.bbs.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpabook.jpashop.account.domain.AccountRepository;
import com.jpabook.jpashop.bbs.domain.Post;
import com.jpabook.jpashop.bbs.domain.PostRepository;
import com.jpabook.jpashop.bbs.web.dto.PostSaveRequestDto;
import com.jpabook.jpashop.bbs.web.dto.PostUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BbsControllerTest {

    @Autowired private PostRepository postRepository;
    @Autowired private AccountRepository accountRepository;

    @Autowired private WebApplicationContext context;
    @Autowired private TestRestTemplate restTemplate;
    @LocalServerPort private int port;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @AfterEach
    public void clear() throws Exception {
        accountRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    public void 메인페이지_로딩() {
        //when
        String body = this.restTemplate.getForObject("/bbs", String.class);

        //then
        assertThat(body).contains("게시판 서비스");
    }

    @Test
    public void post_등록() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/bbs/create";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Post_수정() throws Exception {
        //given
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

        String url = "http://localhost:" + port + "/bbs/update/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}