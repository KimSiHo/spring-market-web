package com.jpabook.jpashop.bbs.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.web.CurrentUser;
import com.jpabook.jpashop.bbs.domain.Post;
import com.jpabook.jpashop.bbs.domain.PostRepository;
import com.jpabook.jpashop.bbs.service.BBSService;
import com.jpabook.jpashop.bbs.web.dto.PostResponseDto;
import com.jpabook.jpashop.bbs.web.dto.PostSaveRequestDto;
import com.jpabook.jpashop.bbs.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BBSController {

    private final PostRepository postRepository;
    private final BBSService bbsService;

    @GetMapping("/bbs")
    public String index(@PageableDefault Pageable pageable, Model model) {
        Page<Post> postList = bbsService.getPostList(pageable);
        /*List<PostListResponseDto> posts = postList.stream().map(PostListResponseDto::new).collect(Collectors.toList());*/

        model.addAttribute("today", LocalDate.now());
        model.addAttribute("postList", postList);

        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                postList.getTotalElements(), postList.getTotalPages(), postList.getSize(),
                postList.getNumber(), postList.getNumberOfElements());

        return "bbs/index";
    }

    @GetMapping("/bbs/create")
    public String postCreate() {
        return "bbs/post-create";
    }

    @GetMapping("/bbs/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).get();
        PostResponseDto postResponseDto = new PostResponseDto(post);
        model.addAttribute("post", postResponseDto);

        return "bbs/post-update";
    }

    @PostMapping("/bbs/create")
    public String save(@CurrentUser Account account, @RequestBody PostSaveRequestDto requestDto) {
        requestDto.setWriter(account);
        Post post = requestDto.toEntity();
        postRepository.save(post);

        return "redirect:/bbs";
    }

    @Transactional
    @PutMapping("/bbs/update/{id}")
    @ResponseBody
    public Long update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        System.out.println("==========enter===============");
        Post post = postRepository.findById(id).get();
        post.update(requestDto.getTitle(), requestDto.getContent());
        System.out.println("post = " + post.toString());
        return post.getId();
    }

    @Transactional
    @DeleteMapping("/bbs/delete/{id}")
    @ResponseBody
    public Long delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return id;
    }
}
