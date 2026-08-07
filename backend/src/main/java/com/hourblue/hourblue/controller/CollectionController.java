package com.hourblue.hourblue.controller;

import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.service.CollectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<PublicCollectionResponse> listCollections() {
        return collectionService.listCollections();
    }

    @GetMapping("/{slug}")
    public PublicCollectionResponse getCollection(@PathVariable String slug) {
        return collectionService.getCollectionBySlug(slug);
    }
}