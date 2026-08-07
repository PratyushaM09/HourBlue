package com.hourblue.hourblue.repository;

import com.hourblue.hourblue.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findBySlug(String slug);

    boolean existsBySlug(String slug);

    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Related-posts legacy helper: same place + same date, excluding the current post.
    List<Post> findTop8ByPlaceAndCapturedDateAndIdNot(String place, LocalDate capturedDate, Long id);

    @Query("""
        SELECT DISTINCT p FROM Post p JOIN p.collections c
        WHERE c.id IN :collectionIds AND p.id <> :excludeId
        ORDER BY p.createdAt DESC
        """)
    List<Post> findByCollectionIdsExcluding(@Param("collectionIds") List<Long> collectionIds,
                                             @Param("excludeId") Long excludeId);

    @Query("""
        SELECT DISTINCT p FROM Post p JOIN p.moods m
        WHERE m.id IN :moodIds AND p.id <> :excludeId
        ORDER BY p.createdAt DESC
        """)
    List<Post> findByMoodIdsExcluding(@Param("moodIds") List<Long> moodIds,
                                       @Param("excludeId") Long excludeId);

    List<Post> findByTagsContainingIgnoreCaseAndIdNotOrderByCreatedAtDesc(String tag, Long id);

    // Search (Section 3.6) - simple LIKE-based search across title/caption/tags/place/collection/mood.
    // Can be swapped for a native FULLTEXT MATCH...AGAINST query later if performance requires it.
    @Query("""
        SELECT DISTINCT p FROM Post p
        LEFT JOIN p.collections c
        LEFT JOIN p.moods m
        WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(p.caption) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(p.tags) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(p.place) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(m.name) LIKE LOWER(CONCAT('%', :q, '%'))
        """)
    Page<Post> search(@Param("q") String query, Pageable pageable);

    List<Post> findTop4ByIsFeaturedTrueOrderByCreatedAtDesc();

    List<Post> findTop4ByOrderByCreatedAtDesc();

    List<Post> findTop8ByIsFeaturedTrueAndIdNotOrderByCreatedAtDesc(Long id);

    List<Post> findTop8ByIdNotOrderByCreatedAtDesc(Long id);
}
