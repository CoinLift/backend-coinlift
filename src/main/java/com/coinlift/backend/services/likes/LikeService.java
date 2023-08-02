package com.coinlift.backend.services.likes;

import java.util.UUID;

public interface LikeService {

    void addLike(UUID postId);

    void removeLike(UUID postId);
}
