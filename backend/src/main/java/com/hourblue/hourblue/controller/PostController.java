package com.hourblue.hourblue.controller;

import com.hourblue.hourblue.dto.PublicPostDetailResponse;
import com.hourblue.hourblue.dto.PublicPostSummaryResponse;
import com.hourblue.hourblue.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PublicPostSummaryResponse> listPosts(@PageableDefault(size = 24) Pageable pageable) {
        return postService.listPosts(pageable);
    }

    @GetMapping("/{slug}")
    public PublicPostDetailResponse getPost(@PathVariable String slug) {
        return postService.getPostBySlug(slug);
    }
}
