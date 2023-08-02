package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.comments.CommentRequestDto;
import com.coinlift.backend.dtos.comments.CommentResponseDto;
import com.coinlift.backend.services.comments.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/replies")
@Tag(name = "Replies Controller", description = "APIs related to managing replies for comments")
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
    @Operation(summary = "Create a new reply to a specific comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reply created successfully", content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/{commentId}")
    public ResponseEntity<UUID> replyToComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
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
    @Operation(summary = "Get a paginated list of replies for a specific comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved replies", content = @Content(schema = @Schema(implementation = CommentResponseDto.class)))
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<List<CommentResponseDto>> getReplies(@PathVariable("commentId") UUID commentId,
                                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getReplies(commentId, page, size));
    }
}
