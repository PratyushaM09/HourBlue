package com.hourblue.hourblue.model;

import com.hourblue.hourblue.model.enums.Weather;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "thumbnail_url", nullable = false, length = 500)
    private String thumbnailUrl;

    @Column(name = "medium_url", nullable = false, length = 500)
    private String mediumUrl;

    @Column(length = 500)
    private String caption;

    @Column(length = 255)
    private String place;

    @Column(name = "captured_date")
    private LocalDate capturedDate;

    @Column(name = "captured_time")
    private LocalTime capturedTime;

    @Enumerated(EnumType.STRING)
    private Weather weather;

    @Column(name = "is_featured", nullable = false)
    private boolean featured = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "photo_collection",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "collection_id")
    )
    private Set<Collection> collections = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "photo_mood",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    private Set<Mood> moods = new HashSet<>();

    public Photo() {
    }

    // --- Getters & setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMediumUrl() {
        return mediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        this.mediumUrl = mediumUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(LocalDate capturedDate) {
        this.capturedDate = capturedDate;
    }

    public LocalTime getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(LocalTime capturedTime) {
        this.capturedTime = capturedTime;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Collection> getCollections() {
        return collections;
    }

    public void setCollections(Set<Collection> collections) {
        this.collections = collections;
    }

    public Set<Mood> getMoods() {
        return moods;
    }

    public void setMoods(Set<Mood> moods) {
        this.moods = moods;
    }
}
