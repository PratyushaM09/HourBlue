package com.hourblue.hourblue.controller;

import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.exception.CollectionNotFoundException;
import com.hourblue.hourblue.exception.GlobalExceptionHandler;
import com.hourblue.hourblue.service.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CollectionControllerTest {

    private StubCollectionService collectionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        collectionService = new StubCollectionService();
        mockMvc = MockMvcBuilders.standaloneSetup(new CollectionController(collectionService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void listCollectionsReturnsPublicCollections() throws Exception {
        collectionService.collections = List.of(collection("skies", "Skies"));

        mockMvc.perform(get("/api/collections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slug").value("skies"))
                .andExpect(jsonPath("$[0].name").value("Skies"));
    }

    @Test
    void getCollectionReturnsPublicCollection() throws Exception {
        collectionService.collectionBySlug.put("skies", collection("skies", "Skies"));

        mockMvc.perform(get("/api/collections/skies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.slug").value("skies"))
                .andExpect(jsonPath("$.name").value("Skies"));
    }

    @Test
    void getCollectionReturns404WhenCollectionDoesNotExist() throws Exception {
        collectionService.notFoundSlugs.add("missing");

        mockMvc.perform(get("/api/collections/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Collection not found: missing"))
                .andExpect(jsonPath("$.status").value(404));
    }

    private PublicCollectionResponse collection(String slug, String name) {
        return new PublicCollectionResponse(name, slug);
    }

    private static class StubCollectionService extends CollectionService {

        private List<PublicCollectionResponse> collections = List.of();
        private final Map<String, PublicCollectionResponse> collectionBySlug = new HashMap<>();
        private final List<String> notFoundSlugs = new ArrayList<>();

        StubCollectionService() {
            super(null);
        }

        @Override
        public List<PublicCollectionResponse> listCollections() {
            return collections;
        }

        @Override
        public PublicCollectionResponse getCollectionBySlug(String slug) {
            if (notFoundSlugs.contains(slug)) {
                throw new CollectionNotFoundException(slug);
            }
            return collectionBySlug.get(slug);
        }
    }
}