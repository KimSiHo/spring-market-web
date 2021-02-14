package com.jpabook.jpashop.bbs.web;


import com.jpabook.jpashop.bbs.service.PostsService;
import com.jpabook.jpashop.bbs.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/bbs")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "bbs/index";
    }

    @GetMapping("/bbs/posts/save")
    public String postsSave() {
        return "bbs/posts-save";
    }

    @GetMapping("/bbs/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "bbs/posts-update";
    }
}
