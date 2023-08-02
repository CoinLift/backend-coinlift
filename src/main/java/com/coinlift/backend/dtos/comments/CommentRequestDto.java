package com.coinlift.backend.dtos.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentRequestDto(
        @NotBlank
        @Schema(description = "The content of the comment.", example = "This place looks beautiful!")
        String content
) {
}