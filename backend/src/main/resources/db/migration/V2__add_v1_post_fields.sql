-- Add V1 post fields for Pinterest-adjacent ideas and monetization-ready links.

ALTER TABLE post
    ADD COLUMN title VARCHAR(180) NULL AFTER slug,
    ADD COLUMN content_type ENUM('IMAGE','ARTICLE','VIDEO','PRODUCT','OTHER') NOT NULL DEFAULT 'IMAGE' AFTER title,
    ADD COLUMN pinterest_url VARCHAR(500) NULL AFTER medium_url,
    ADD COLUMN external_url VARCHAR(500) NULL AFTER pinterest_url,
    ADD COLUMN affiliate_url VARCHAR(500) NULL AFTER external_url,
    ADD COLUMN tags VARCHAR(500) NULL AFTER caption;

UPDATE post
SET title = LEFT(COALESCE(NULLIF(caption, ''), slug), 180)
WHERE title IS NULL;

ALTER TABLE post
    MODIFY COLUMN title VARCHAR(180) NOT NULL;

ALTER TABLE post DROP INDEX ftx_post_caption_place;
CREATE FULLTEXT INDEX ftx_post_search ON post (title, caption, tags, place);
