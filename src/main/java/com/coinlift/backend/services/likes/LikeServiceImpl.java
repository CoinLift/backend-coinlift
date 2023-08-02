package com.coinlift.backend.services.likes;

import com.coinlift.backend.entities.Like;
import com.coinlift.backend.entities.MyUserDetails;
import com.coinlift.backend.entities.Post;
import com.coinlift.backend.entities.User;
import com.coinlift.backend.exceptions.DeniedAccessException;
import com.coinlift.backend.exceptions.ResourceNotFoundException;
import com.coinlift.backend.repositories.LikeRepository;
import com.coinlift.backend.repositories.PostRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void addLike(UUID postId) {
        User user = getUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        if (likeRepository.existsByUserAndPostId(user, postId)) {
            throw new IllegalArgumentException("You already like this post!");
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        post.setLikeCount(post.getLikeCount() + 1);

        likeRepository.save(like);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void removeLike(UUID postId) {
        User user = getUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        Like like = likeRepository.getLikeByUserAndPostId(user, postId);
        if (like == null) {
            throw new IllegalArgumentException("You can't remove like from this post!");
        }

        post.setLikeCount(post.getLikeCount() - 1);

        likeRepository.delete(like);
        postRepository.save(post);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new DeniedAccessException("You can't do it before authenticate!");
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        return userDetails.user();
    }
}
