package com.hourblue.hourblue.service;

import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.exception.CollectionNotFoundException;
import com.hourblue.hourblue.model.Collection;
import com.hourblue.hourblue.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Transactional(readOnly = true)
    public List<PublicCollectionResponse> listCollections() {
        return collectionRepository.findAllByOrderByDisplayOrderAscNameAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicCollectionResponse getCollectionBySlug(String slug) {
        Collection collection = collectionRepository.findBySlug(slug)
                .orElseThrow(() -> new CollectionNotFoundException(slug));

        return toResponse(collection);
    }

    private PublicCollectionResponse toResponse(Collection collection) {
        return new PublicCollectionResponse(collection.getName(), collection.getSlug());
    }
}