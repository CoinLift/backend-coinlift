package com.coinlift.backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record FollowerResponseDto(
        @Schema(description = "The username of the follower.", example = "john_doe")
        String username,

        @Schema(description = "The unique identifier of the follower.", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id
) {
}
