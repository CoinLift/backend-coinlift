ALTER TABLE comments
    ADD COLUMN parent_comment_id UUID,
    ADD CONSTRAINT fk_parent_comment
        FOREIGN KEY (parent_comment_id)
            REFERENCES comments (id)
            ON DELETE CASCADE;
