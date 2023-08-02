package com.coinlift.backend.dtos.posts;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PostShortResponseDto(

        @Schema(description = "The unique identifier of the post.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID uuid,

        @Schema(description = "The content of the post.", example = "Check out this amazing view!")
        String content,

        @Schema(description = "The image associated with the post as a byte array.", example = "SGVsbG8gV29ybGQ=")
        byte[] image,

        @Schema(description = "The number of likes on the post.", example = "50")
        Integer likeCount
) {
}
