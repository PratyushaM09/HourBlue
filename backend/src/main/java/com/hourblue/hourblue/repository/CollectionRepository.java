package com.hourblue.hourblue.repository;

import com.hourblue.hourblue.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Optional<Collection> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Collection> findAllByOrderByDisplayOrderAscNameAsc();
}
