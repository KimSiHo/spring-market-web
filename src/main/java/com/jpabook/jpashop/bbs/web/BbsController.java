package com.jpabook.jpashop.bbs.web;

import com.jpabook.jpashop.account.domain.Account;
import com.jpabook.jpashop.account.service.CurrentUser;
import com.jpabook.jpashop.bbs.domain.Post;
import com.jpabook.jpashop.bbs.domain.PostRepository;
import com.jpabook.jpashop.bbs.web.dto.PostListResponseDto;
import com.jpabook.jpashop.bbs.web.dto.PostResponseDto;
import com.jpabook.jpashop.bbs.web.dto.PostSaveRequestDto;
import com.jpabook.jpashop.bbs.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BbsController {

    private final PostRepository postRepository;

    @GetMapping("/bbs")
    public String index(Model model) {
        List<Post> postList = postRepository.findAllByOrderByIdDesc();
        List<PostListResponseDto> posts = postList.stream().map(PostListResponseDto::new).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "bbs/index";
    }

    @GetMapping("/bbs/create")
    public String postCreate() {
        return "post-create";
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
    public String update(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id).get();
        post.update(requestDto.getTitle(), requestDto.getContent());

        return "redirect:/bbs";
    }

    @Transactional
    @DeleteMapping("/bbs/delete/{id}")
    public String delete(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/bbs";
    }
}
