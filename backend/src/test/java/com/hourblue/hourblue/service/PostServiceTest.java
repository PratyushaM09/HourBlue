package com.hourblue.hourblue.service;

import com.hourblue.hourblue.dto.PublicPostDetailResponse;
import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.dto.PublicMoodResponse;
import com.hourblue.hourblue.dto.PublicPostSummaryResponse;
import com.hourblue.hourblue.exception.PostNotFoundException;
import com.hourblue.hourblue.model.Collection;
import com.hourblue.hourblue.model.Mood;
import com.hourblue.hourblue.model.Post;
import com.hourblue.hourblue.model.enums.ContentType;
import com.hourblue.hourblue.model.enums.Weather;
import com.hourblue.hourblue.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void listPostsMapsPostsToPublicSummaries() {
        PageRequest pageable = PageRequest.of(0, 24);
        Post post = post(1L, "misty-morning", "Misty Morning");

        when(postRepository.findAllByOrderByCreatedAtDesc(pageable))
                .thenReturn(new PageImpl<>(List.of(post), pageable, 1));

        Page<PublicPostSummaryResponse> response = postService.listPosts(pageable);

        assertThat(response.getContent()).hasSize(1);
        PublicPostSummaryResponse summary = response.getContent().get(0);
        assertThat(summary.slug()).isEqualTo("misty-morning");
        assertThat(summary.title()).isEqualTo("Misty Morning");
        assertThat(summary.contentType()).isEqualTo(ContentType.IMAGE);
        assertThat(summary.collections()).extracting(PublicCollectionResponse::slug).containsExactly("skies");
        assertThat(summary.moods()).extracting(PublicMoodResponse::slug).containsExactly("quiet");
    }

    @Test
    void getPostBySlugMapsDetailAndRelatedPostsInPriorityOrder() {
        Post post = post(1L, "misty-morning", "Misty Morning");
        Post samePlace = post(2L, "same-place", "Same Place");
        Post sharedCollection = post(3L, "shared-collection", "Shared Collection");
        Post sharedMood = post(4L, "shared-mood", "Shared Mood");

        when(postRepository.findBySlug("misty-morning")).thenReturn(Optional.of(post));
        when(postRepository.findTop8ByPlaceAndCapturedDateAndIdNot("Jaipur", LocalDate.of(2026, 8, 1), 1L))
                .thenReturn(List.of(samePlace));
        when(postRepository.findByCollectionIdsExcluding(List.of(10L), 1L))
                .thenReturn(List.of(samePlace, sharedCollection));
        when(postRepository.findByMoodIdsExcluding(List.of(20L), 1L))
                .thenReturn(List.of(sharedMood));

        PublicPostDetailResponse response = postService.getPostBySlug("misty-morning");

        assertThat(response.slug()).isEqualTo("misty-morning");
        assertThat(response.imageUrl()).isEqualTo("https://cdn.example.com/full/misty-morning.jpg");
        assertThat(response.pinterestUrl()).isEqualTo("https://pinterest.example/misty-morning");
        assertThat(response.externalUrl()).isEqualTo("https://source.example/misty-morning");
        assertThat(response.affiliateUrl()).isEqualTo("https://affiliate.example/misty-morning");
        assertThat(response.relatedPosts())
                .extracting(PublicPostSummaryResponse::slug)
                .containsExactly("same-place", "shared-collection", "shared-mood");
    }

    @Test
    void getPostBySlugThrowsWhenPostDoesNotExist() {
        when(postRepository.findBySlug("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostBySlug("missing"))
                .isInstanceOf(PostNotFoundException.class)
                .hasMessage("Post not found: missing");
    }

    @Test
    void getPostBySlugSkipsRelatedQueriesWhenMetadataIsMissing() {
        Post post = post(1L, "simple", "Simple");
        post.setPlace(null);
        post.setCapturedDate(null);
        post.setCollections(java.util.Set.of());
        post.setMoods(java.util.Set.of());

        when(postRepository.findBySlug("simple")).thenReturn(Optional.of(post));

        PublicPostDetailResponse response = postService.getPostBySlug("simple");

        assertThat(response.relatedPosts()).isEmpty();
        verify(postRepository).findBySlug("simple");
    }

    private Post post(Long id, String slug, String title) {
        Post post = new Post();
        post.setId(id);
        post.setSlug(slug);
        post.setTitle(title);
        post.setContentType(ContentType.IMAGE);
        post.setImageUrl("https://cdn.example.com/full/" + slug + ".jpg");
        post.setThumbnailUrl("https://cdn.example.com/thumb/" + slug + ".jpg");
        post.setMediumUrl("https://cdn.example.com/medium/" + slug + ".jpg");
        post.setCaption("A quiet visual idea.");
        post.setTags("quiet, blue hour");
        post.setPlace("Jaipur");
        post.setCapturedDate(LocalDate.of(2026, 8, 1));
        post.setCapturedTime(LocalTime.of(18, 30));
        post.setWeather(Weather.CLOUDY);
        post.setFeatured(true);
        post.setPinterestUrl("https://pinterest.example/" + slug);
        post.setExternalUrl("https://source.example/" + slug);
        post.setAffiliateUrl("https://affiliate.example/" + slug);
        post.setCollections(java.util.Set.of(collection()));
        post.setMoods(java.util.Set.of(mood()));
        return post;
    }

    private Collection collection() {
        Collection collection = new Collection();
        collection.setId(10L);
        collection.setName("Skies");
        collection.setSlug("skies");
        collection.setDisplayOrder(1);
        return collection;
    }

    private Mood mood() {
        Mood mood = new Mood();
        mood.setId(20L);
        mood.setName("Quiet");
        mood.setSlug("quiet");
        mood.setDisplayOrder(1);
        return mood;
    }
}
