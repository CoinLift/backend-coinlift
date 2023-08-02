package com.coinlift.backend.controllers;

import com.coinlift.backend.services.likes.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> likePost(@PathVariable("userId") UUID postId) {
        likeService.addLike(postId);
        return ResponseEntity.ok("Like added successfully");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> unlikePost(@PathVariable("userId") UUID postId) {
        likeService.removeLike(postId);
        return ResponseEntity.ok("You added successfully");
    }
}
