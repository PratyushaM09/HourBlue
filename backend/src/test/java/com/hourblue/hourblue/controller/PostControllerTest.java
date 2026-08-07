package com.hourblue.hourblue.controller;

import com.hourblue.hourblue.dto.PublicPostDetailResponse;
import com.hourblue.hourblue.dto.PublicPostSummaryResponse;
import com.hourblue.hourblue.exception.GlobalExceptionHandler;
import com.hourblue.hourblue.exception.PostNotFoundException;
import com.hourblue.hourblue.model.enums.ContentType;
import com.hourblue.hourblue.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest {

    private StubPostService postService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        postService = new StubPostService();
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void listPostsReturnsPublicPostSummaries() throws Exception {
        PublicPostSummaryResponse summary = summary("misty-morning", "Misty Morning");

        postService.posts = new PageImpl<>(List.of(summary), PageRequest.of(0, 24), 1);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].slug").value("misty-morning"))
                .andExpect(jsonPath("$.content[0].title").value("Misty Morning"))
                .andExpect(jsonPath("$.content[0].contentType").value("IMAGE"));
    }

    @Test
    void getPostReturnsPublicPostDetail() throws Exception {
        postService.details.put("misty-morning", detail("misty-morning", "Misty Morning"));

        mockMvc.perform(get("/api/posts/misty-morning"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("misty-morning"))
                .andExpect(jsonPath("$.title").value("Misty Morning"))
                .andExpect(jsonPath("$.relatedPosts[0].slug").value("related-post"));
    }

    @Test
    void getRelatedPostsReturnsPublicPostSummaries() throws Exception {
        postService.relatedPosts.put("misty-morning", List.of(summary("related-post", "Related Post")));

        mockMvc.perform(get("/api/posts/misty-morning/related"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("related-post"))
                .andExpect(jsonPath("$[0].title").value("Related Post"));
    }

    @Test
    void getPostReturns404WhenPostDoesNotExist() throws Exception {
        postService.notFoundSlugs.add("missing");

        mockMvc.perform(get("/api/posts/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Post not found: missing"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void getRelatedPostsReturns404WhenPostDoesNotExist() throws Exception {
        postService.notFoundSlugs.add("missing");

        mockMvc.perform(get("/api/posts/missing/related"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Post not found: missing"))
                .andExpect(jsonPath("$.status").value(404));
    }

    private PublicPostSummaryResponse summary(String slug, String title) {
        return new PublicPostSummaryResponse(
                slug,
                title,
                ContentType.IMAGE,
                "https://cdn.example.com/thumb/" + slug + ".jpg",
                "https://cdn.example.com/medium/" + slug + ".jpg",
                "A quiet visual idea.",
                "Jaipur",
                LocalDate.of(2026, 8, 1),
                true,
                List.of(),
                List.of()
        );
    }

    private PublicPostDetailResponse detail(String slug, String title) {
        return new PublicPostDetailResponse(
                slug,
                title,
                ContentType.IMAGE,
                "https://cdn.example.com/full/" + slug + ".jpg",
                "https://cdn.example.com/thumb/" + slug + ".jpg",
                "https://cdn.example.com/medium/" + slug + ".jpg",
                "A quiet visual idea.",
                "quiet, blue hour",
                "Jaipur",
                LocalDate.of(2026, 8, 1),
                null,
                null,
                true,
                "https://pinterest.example/" + slug,
                "https://source.example/" + slug,
                "https://affiliate.example/" + slug,
                List.of(),
                List.of(),
                List.of(summary("related-post", "Related Post"))
        );
    }

    private static class StubPostService extends PostService {

        private Page<PublicPostSummaryResponse> posts = Page.empty();
        private final Map<String, PublicPostDetailResponse> details = new HashMap<>();
        private final Map<String, List<PublicPostSummaryResponse>> relatedPosts = new HashMap<>();
        private final List<String> notFoundSlugs = new ArrayList<>();

        StubPostService() {
            super(null);
        }

        @Override
        public Page<PublicPostSummaryResponse> listPosts(Pageable pageable) {
            return posts;
        }

        @Override
        public PublicPostDetailResponse getPostBySlug(String slug) {
            if (notFoundSlugs.contains(slug)) {
                throw new PostNotFoundException(slug);
            }
            return details.get(slug);
        }

        @Override
        public List<PublicPostSummaryResponse> getRelatedPostsBySlug(String slug) {
            if (notFoundSlugs.contains(slug)) {
                throw new PostNotFoundException(slug);
            }
            return relatedPosts.getOrDefault(slug, List.of());
        }
    }
}
