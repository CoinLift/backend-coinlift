package com.coinlift.backend.dtos.posts;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PostRequestDto(
        @NotBlank
        @Schema(description = "The content of the post.", example = "Check out this amazing view!")
        String content
) {
}