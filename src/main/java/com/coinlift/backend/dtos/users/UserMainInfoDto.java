package com.coinlift.backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserMainInfoDto(
        @Schema(description = "The username of the user.", example = "john_doe")
        String username,

        @Schema(description = "The user's profile image as a byte array.", example = "SGVsbG8gV29ybGQ=")
        byte[] profileImage,

        @Schema(description = "Indicates whether the current user is following this user.", example = "true")
        boolean isFollowing
) {
}
