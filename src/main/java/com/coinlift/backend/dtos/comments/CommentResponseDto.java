package com.coinlift.backend.dtos.comments;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDto(

        @Schema(description = "The unique identifier of the comment.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "The unique identifier of the comment creator.", example = "456e4567-e89b-12d3-a456-426614174000")
        UUID creatorId,

        @Schema(description = "The content of the comment.", example = "This place looks beautiful!")
        String content,

        @Schema(description = "The date and time when the comment was created.", example = "2023-07-31T12:34:56")
        LocalDateTime createdAt,

        @Schema(description = "Indicates whether the current user is the creator of the comment.", example = "true")
        boolean isCommentCreator,

        @Schema(description = "Indicates whether the comment has replies.", example = "false")
        boolean isRepliesExist
) {
}
