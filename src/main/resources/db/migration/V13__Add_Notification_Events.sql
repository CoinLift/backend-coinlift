CREATE TABLE events
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(20)  NOT NULL,
    text VARCHAR(255) NOT NULL
);

INSERT INTO events (type, text)
VALUES ('COMMENT', 'commented on your post');

INSERT INTO events (type, text)
VALUES ('FOLLOW', 'followed you');

INSERT INTO events (type, text)
VALUES ('LIKE', 'liked your post');

INSERT INTO events (type, text)
VALUES ('REPLY', 'replied to your comment');

CREATE TABLE notifications
(
    id                   UUID PRIMARY KEY,
    user_to_notify       UUID        NOT NULL,
    user_who_fired_event VARCHAR(30) NOT NULL,
    event_id             INTEGER     NOT NULL,
    seen_by_user         BOOLEAN DEFAULT FALSE
);

ALTER TABLE notifications
    ADD CONSTRAINT fk_event_notification
        FOREIGN KEY (event_id)
            REFERENCES events (id);