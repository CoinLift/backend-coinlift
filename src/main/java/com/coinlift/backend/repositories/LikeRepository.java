package com.coinlift.backend.repositories;

import com.coinlift.backend.entities.Like;
import com.coinlift.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {

    Like getLikeByUserAndPostId(User user, UUID postId);

    boolean existsByUserAndPostId(User user, UUID postId);
}
