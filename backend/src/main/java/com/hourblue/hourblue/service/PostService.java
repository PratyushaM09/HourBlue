package com.hourblue.hourblue.service;

import com.hourblue.hourblue.dto.PublicCollectionResponse;
import com.hourblue.hourblue.dto.PublicMoodResponse;
import com.hourblue.hourblue.dto.PublicPostDetailResponse;
import com.hourblue.hourblue.dto.PublicPostSummaryResponse;
import com.hourblue.hourblue.exception.PostNotFoundException;
import com.hourblue.hourblue.model.Collection;
import com.hourblue.hourblue.model.Mood;
import com.hourblue.hourblue.model.Post;
import com.hourblue.hourblue.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private static final int RELATED_POST_LIMIT = 8;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public Page<PublicPostSummaryResponse> listPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::toSummaryResponse);
    }

    @Transactional(readOnly = true)
    public PublicPostDetailResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new PostNotFoundException(slug));

        return toDetailResponse(post, findRelatedPosts(post));
    }

    private List<Post> findRelatedPosts(Post post) {
        Map<Long, Post> relatedById = new LinkedHashMap<>();

        if (post.getPlace() != null && post.getCapturedDate() != null) {
            addRelatedPosts(relatedById,
                    postRepository.findTop8ByPlaceAndCapturedDateAndIdNot(
                            post.getPlace(), post.getCapturedDate(), post.getId()
                    ));
        }

        if (relatedById.size() < RELATED_POST_LIMIT) {
            List<Long> collectionIds = post.getCollections().stream()
                    .map(Collection::getId)
                    .toList();
            if (!collectionIds.isEmpty()) {
                addRelatedPosts(relatedById,
                        postRepository.findByCollectionIdsExcluding(collectionIds, post.getId()));
            }
        }

        if (relatedById.size() < RELATED_POST_LIMIT) {
            List<Long> moodIds = post.getMoods().stream()
                    .map(Mood::getId)
                    .toList();
            if (!moodIds.isEmpty()) {
                addRelatedPosts(relatedById,
                        postRepository.findByMoodIdsExcluding(moodIds, post.getId()));
            }
        }

        return new ArrayList<>(relatedById.values()).stream()
                .limit(RELATED_POST_LIMIT)
                .toList();
    }

    private void addRelatedPosts(Map<Long, Post> relatedById, List<Post> posts) {
        for (Post post : posts) {
            if (post.getId() != null && relatedById.size() < RELATED_POST_LIMIT) {
                relatedById.putIfAbsent(post.getId(), post);
            }
        }
    }

    private PublicPostDetailResponse toDetailResponse(Post post, List<Post> relatedPosts) {
        return new PublicPostDetailResponse(
                post.getSlug(),
                post.getTitle(),
                post.getContentType(),
                post.getImageUrl(),
                post.getThumbnailUrl(),
                post.getMediumUrl(),
                post.getCaption(),
                post.getTags(),
                post.getPlace(),
                post.getCapturedDate(),
                post.getCapturedTime(),
                post.getWeather(),
                post.isFeatured(),
                post.getPinterestUrl(),
                post.getExternalUrl(),
                post.getAffiliateUrl(),
                toCollectionResponses(post),
                toMoodResponses(post),
                relatedPosts.stream().map(this::toSummaryResponse).toList()
        );
    }

    private PublicPostSummaryResponse toSummaryResponse(Post post) {
        return new PublicPostSummaryResponse(
                post.getSlug(),
                post.getTitle(),
                post.getContentType(),
                post.getThumbnailUrl(),
                post.getMediumUrl(),
                post.getCaption(),
                post.getPlace(),
                post.getCapturedDate(),
                post.isFeatured(),
                toCollectionResponses(post),
                toMoodResponses(post)
        );
    }

    private List<PublicCollectionResponse> toCollectionResponses(Post post) {
        return post.getCollections().stream()
                .sorted(Comparator.comparingInt(Collection::getDisplayOrder)
                        .thenComparing(Collection::getName))
                .map(collection -> new PublicCollectionResponse(collection.getName(), collection.getSlug()))
                .toList();
    }

    private List<PublicMoodResponse> toMoodResponses(Post post) {
        return post.getMoods().stream()
                .sorted(Comparator.comparingInt(Mood::getDisplayOrder)
                        .thenComparing(Mood::getName))
                .map(mood -> new PublicMoodResponse(mood.getName(), mood.getSlug()))
                .toList();
    }
}
