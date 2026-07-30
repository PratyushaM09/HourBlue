package com.hourblue.hourblue.repository;

import com.hourblue.hourblue.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MoodRepository extends JpaRepository<Mood, Long> {
    Optional<Mood> findBySlug(String slug);
    boolean existsBySlug(String slug);
    List<Mood> findAllByOrderByDisplayOrderAscNameAsc();
}
