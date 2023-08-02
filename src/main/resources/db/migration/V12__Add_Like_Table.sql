-- Add likeCount column to the existing posts table
ALTER TABLE posts
    ADD COLUMN like_count INT DEFAULT 0;

-- Create the likes table
CREATE TABLE likes
(
    id      UUID PRIMARY KEY,
    user_id UUID REFERENCES users (id),
    post_id UUID REFERENCES posts (id)
);

-- Add foreign key constraints to the likes table
ALTER TABLE likes
    ADD CONSTRAINT FK_USERS_LIKES FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE likes
    ADD CONSTRAINT FK_POSTS_LIKES FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE;
