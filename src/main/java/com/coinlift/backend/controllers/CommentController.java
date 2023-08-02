package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.comments.CommentRequestDto;
import com.coinlift.backend.dtos.comments.CommentResponseDto;
import com.coinlift.backend.services.comments.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/v1/posts/{postId}")
@CrossOrigin("*")
@Tag(name = "Comment Controller", description = "APIs related to managing comments for posts")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Endpoint to fetch all comments of a specific post.
     *
     * @param uuid The unique ID of the post to fetch comments for.
     * @param page The page number for pagination (default: 0).
     * @param size The number of comments per page (default: 20).
     * @return A response entity containing the list of comments for the post.
     */
    @Operation(summary = "Fetch all comments of a specific post")
    @ApiResponse(
            responseCode = "200",
            description = "Comments fetched successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseDto.class)))
    )
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDto>> getAllComments(@PathVariable("postId") UUID uuid,
                                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getComments(uuid, page, size));
    }

    /**
     * Endpoint to create a new comment for a specific post.
     *
     * @param commentRequestDto The comment request containing the comment details.
     * @param uuid              The unique ID of the post to create the comment for.
     * @return A response entity containing the ID of the created comment and HTTP status 201 (Created).
     */
    @Operation(summary = "Create a new comment for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<UUID> createComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                              @PathVariable("postId") UUID uuid) {
        return new ResponseEntity<>(commentService.createComment(commentRequestDto, uuid), HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing comment on a specific post.
     *
     * @param commentRequestDto The updated comment details.
     * @param postId            The unique ID of the post to update the comment for.
     * @param commentId         The unique ID of the comment to be updated.
     * @return A response entity containing the updated comment details and HTTP status 200 (OK).
     */
    @Operation(summary = "Update an existing comment on a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(schema = @Schema(implementation = CommentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@RequestBody @Valid CommentRequestDto commentRequestDto,
                                                            @PathVariable("postId") UUID postId,
                                                            @PathVariable("commentId") UUID commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentRequestDto, postId, commentId), HttpStatus.OK);
    }

    /**
     * Endpoint to remove a comment from a specific post.
     *
     * @param postId    The unique ID of the post to remove the comment from.
     * @param commentId The unique ID of the comment to be removed.
     * @return A response entity with a success message upon successful comment deletion.
     */
    @Operation(summary = "Remove a comment from a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment successfully deleted", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> removeComment(@PathVariable("postId") UUID postId,
                                                @PathVariable("commentId") UUID commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment successfully deleted!");
    }
}
