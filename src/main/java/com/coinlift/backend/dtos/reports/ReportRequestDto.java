package com.coinlift.backend.dtos.reports;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ReportRequestDto(
        @NotBlank
        @Length(min = 3, max = 600)
        String reportMsg
) {
}
