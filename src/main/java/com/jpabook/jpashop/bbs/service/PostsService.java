package com.jpabook.jpashop.bbs.service;

import com.jpabook.jpashop.bbs.domain.posts.Posts;
import com.jpabook.jpashop.bbs.domain.posts.PostsRepository;
import com.jpabook.jpashop.bbs.web.dto.PostsListResponseDto;
import com.jpabook.jpashop.bbs.web.dto.PostsResponseDto;
import com.jpabook.jpashop.bbs.web.dto.PostsSaveRequestDto;
import com.jpabook.jpashop.bbs.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findByOrderByIdDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
