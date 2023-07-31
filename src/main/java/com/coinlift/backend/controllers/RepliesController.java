package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.comments.CommentResponseDto;
import com.coinlift.backend.dtos.comments.CommentRequestDto;
import com.coinlift.backend.services.comments.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/replies")
public class RepliesController {

    private final CommentService commentService;

    public RepliesController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Create a new reply to a specific comment.
     *
     * @param commentRequestDto The {@link CommentRequestDto} containing the reply details.
     * @param commentId         The unique ID of the comment to reply to.
     * @return The ID of the created reply and HTTP status 201 (Created).
     */
    @PostMapping("/{commentId}")
    public ResponseEntity<UUID> replyToComment(@RequestBody CommentRequestDto commentRequestDto,
                                               @PathVariable("commentId") UUID commentId) {
        return new ResponseEntity<>(commentService.createReply(commentRequestDto, commentId), HttpStatus.CREATED);
    }

    /**
     * Get a paginated list of replies for a specific comment.
     *
     * @param commentId The unique ID of the comment to fetch replies for.
     * @param page      The page number for pagination (default: 0).
     * @param size      The number of replies per page (default: 10).
     * @return A list of {@link CommentResponseDto} representing the replies to the comment.
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<List<CommentResponseDto>> getReplies(@PathVariable("commentId") UUID commentId,
                                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getReplies(commentId, page, size));
    }
}
