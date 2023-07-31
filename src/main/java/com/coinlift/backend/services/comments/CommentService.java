package com.coinlift.backend.services.comments;

import com.coinlift.backend.dtos.comments.CommentRequestDto;
import com.coinlift.backend.dtos.comments.CommentResponseDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    List<CommentResponseDto> getComments(UUID postId, int page, int size);

    List<CommentResponseDto> getReplies(UUID commentId, int page, int size);

    UUID createReply(CommentRequestDto commentRequestDto, UUID commentId);

    UUID createComment(CommentRequestDto commentRequestDto, UUID postId);

    CommentResponseDto updateComment(CommentRequestDto commentRequestDto, UUID postId, UUID commentId);

    void deleteComment(UUID postId, UUID commentId);
}
