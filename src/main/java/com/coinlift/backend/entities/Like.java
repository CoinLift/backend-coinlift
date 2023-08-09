package com.coinlift.backend.entities;

import com.coinlift.backend.entities.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USERS_LIKES"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_USERS_LIKES"))
    private Post post;

    public Like(UUID id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public Like() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
