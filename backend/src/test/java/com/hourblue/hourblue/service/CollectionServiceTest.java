package com.hourblue.hourblue.service;

import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.exception.CollectionNotFoundException;
import com.hourblue.hourblue.model.Collection;
import com.hourblue.hourblue.repository.CollectionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    private CollectionService collectionService;

    @Test
    void listCollectionsMapsCollectionsInRepositoryOrder() {
        when(collectionRepository.findAllByOrderByDisplayOrderAscNameAsc())
                .thenReturn(List.of(collection("skies", "Skies"), collection("rooms", "Rooms")));

        List<PublicCollectionResponse> response = collectionService.listCollections();

        assertThat(response)
                .extracting(PublicCollectionResponse::slug)
                .containsExactly("skies", "rooms");
    }

    @Test
    void getCollectionBySlugMapsCollection() {
        when(collectionRepository.findBySlug("skies"))
                .thenReturn(Optional.of(collection("skies", "Skies")));

        PublicCollectionResponse response = collectionService.getCollectionBySlug("skies");

        assertThat(response.name()).isEqualTo("Skies");
        assertThat(response.slug()).isEqualTo("skies");
    }

    @Test
    void getCollectionBySlugThrowsWhenCollectionDoesNotExist() {
        when(collectionRepository.findBySlug("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collectionService.getCollectionBySlug("missing"))
                .isInstanceOf(CollectionNotFoundException.class)
                .hasMessage("Collection not found: missing");
    }

    private Collection collection(String slug, String name) {
        Collection collection = new Collection();
        collection.setSlug(slug);
        collection.setName(name);
        return collection;
    }
}