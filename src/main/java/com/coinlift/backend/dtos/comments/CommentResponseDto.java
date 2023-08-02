package com.coinlift.backend.dtos.comments;

import com.coinlift.backend.dtos.users.UserMainInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CommentResponseDto(

        @Schema(description = "The unique identifier of the comment.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "The content of the comment.", example = "This place looks beautiful!")
        String content,

        @Schema(description = "The date and time when the comment was created.", example = "2023-07-31T12:34:56")
        long commentTime,

        @Schema(description = "Indicates whether the current user is the creator of the comment.", example = "true")
        boolean isCommentCreator,

        @Schema(description = "Indicates whether the comment has replies.", example = "false")
        boolean isRepliesExist,

        UserMainInfoDto owner
) {
}
