package com.hourblue.hourblue.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mood")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    @Column(name = "cover_post_id")
    private Long coverPostId;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    public Mood() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Long getCoverPostId() {
        return coverPostId;
    }

    public void setCoverPostId(Long coverPostId) {
        this.coverPostId = coverPostId;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
