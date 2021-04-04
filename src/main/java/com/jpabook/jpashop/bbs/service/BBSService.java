package com.jpabook.jpashop.bbs.service;

import com.jpabook.jpashop.bbs.domain.Post;
import com.jpabook.jpashop.bbs.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BBSService {

    private final PostRepository postRepository;

    public Page<Post> getPostList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int postPerPage = pageable.getPageSize();

        pageable = PageRequest.of(page, postPerPage, Sort.by(Sort.Direction.ASC, "id"));

        return postRepository.findAll(pageable);
    }
}
