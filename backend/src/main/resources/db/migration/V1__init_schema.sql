-- HourBlue initial schema
-- Mirrors docs/PLANNING.md Section 4.2

CREATE TABLE admin_user (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(100)  NOT NULL,
    password_hash VARCHAR(255)  NOT NULL,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_admin_user_username UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE photo (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    slug           VARCHAR(255) NOT NULL,
    image_url      VARCHAR(500) NOT NULL,
    thumbnail_url  VARCHAR(500) NOT NULL,
    medium_url     VARCHAR(500) NOT NULL,
    caption        VARCHAR(500) NULL,
    place          VARCHAR(255) NULL,
    captured_date  DATE NULL,
    captured_time  TIME NULL,
    weather        ENUM('SUNNY','CLOUDY','FOGGY','RAINY','STORM') NULL,
    is_featured    BOOLEAN NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_photo_slug UNIQUE (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_photo_place_date ON photo (place, captured_date);
CREATE INDEX idx_photo_captured_date ON photo (captured_date);
CREATE FULLTEXT INDEX ftx_photo_caption_place ON photo (caption, place);

CREATE TABLE collection (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    slug            VARCHAR(120) NOT NULL,
    cover_photo_id  BIGINT NULL,
    display_order   INT NOT NULL DEFAULT 0,
    CONSTRAINT uq_collection_name UNIQUE (name),
    CONSTRAINT uq_collection_slug UNIQUE (slug),
    CONSTRAINT fk_collection_cover_photo FOREIGN KEY (cover_photo_id) REFERENCES photo (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE mood (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    slug            VARCHAR(120) NOT NULL,
    cover_photo_id  BIGINT NULL,
    display_order   INT NOT NULL DEFAULT 0,
    CONSTRAINT uq_mood_name UNIQUE (name),
    CONSTRAINT uq_mood_slug UNIQUE (slug),
    CONSTRAINT fk_mood_cover_photo FOREIGN KEY (cover_photo_id) REFERENCES photo (id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE photo_collection (
    photo_id       BIGINT NOT NULL,
    collection_id  BIGINT NOT NULL,
    PRIMARY KEY (photo_id, collection_id),
    CONSTRAINT fk_pc_photo FOREIGN KEY (photo_id) REFERENCES photo (id) ON DELETE CASCADE,
    CONSTRAINT fk_pc_collection FOREIGN KEY (collection_id) REFERENCES collection (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE photo_mood (
    photo_id  BIGINT NOT NULL,
    mood_id   BIGINT NOT NULL,
    PRIMARY KEY (photo_id, mood_id),
    CONSTRAINT fk_pm_photo FOREIGN KEY (photo_id) REFERENCES photo (id) ON DELETE CASCADE,
    CONSTRAINT fk_pm_mood FOREIGN KEY (mood_id) REFERENCES mood (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed the v1 mood list from Section 3.4 (admin can add more later via this same table)
INSERT INTO mood (name, slug, display_order) VALUES
    ('Peaceful', 'peaceful', 1),
    ('Adventure', 'adventure', 2),
    ('Rainy', 'rainy', 3),
    ('Golden', 'golden', 4),
    ('Quiet', 'quiet', 5),
    ('Joyful', 'joyful', 6);
