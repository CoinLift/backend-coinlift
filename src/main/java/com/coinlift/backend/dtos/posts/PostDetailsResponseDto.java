package com.coinlift.backend.dtos.posts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDetailsResponseDto(
        @Schema(description = "The unique identifier of the post.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID uuid,

        @Schema(description = "The unique identifier of the post creator.", example = "456e4567-e89b-12d3-a456-426614174000")
        UUID creatorId,

        @Schema(description = "The content of the post.", example = "Check out this amazing view!")
        String content,

        @Schema(description = "The image associated with the post as a byte array.", example = "SGVsbG8gV29ybGQ=")
        byte[] image,

        @Schema(description = "Indicates whether the current user is the creator of the post.", example = "true")
        boolean isPostCreator,

        @Schema(description = "The date and time when the post was created.", example = "2023-07-31T12:34:56")
        LocalDateTime createdAt,

        @Schema(description = "The number of comments on the post.", example = "10")
        Integer commentCount,

        @Schema(description = "The number of likes on the post.", example = "50")
        Integer likeCount
) {
}
