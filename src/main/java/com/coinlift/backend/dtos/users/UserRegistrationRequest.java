package com.coinlift.backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UserRegistrationRequest(

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9_][a-zA-Z0-9._]*$", message = "Username must start with a word character (a-z, A-Z, 0-9, or underscore) and can only contain word characters (a-z, A-Z, 0-9, underscore, or dot).")
        @Pattern(regexp = "(?!.*\\.\\.).*", message = "Username must not contain two consecutive dots '..'.")
        @Pattern(regexp = "^(?!.*\\.$).*", message = "Username must not end with a dot '.'.")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters in length.")
        @Schema(description = "The desired username for registration.", example = "john_doe")
        String username,

        @NotBlank
        @Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", flags = {Pattern.Flag.CASE_INSENSITIVE})
        @Schema(description = "The email address for registration.", example = "john.doe@example.com")
        String emailAddress,

        @NotNull
        @Size(min = 8)
        @Pattern(regexp = "^[a-zA-Z0-9_].*$", message = "Password must start with a word character (a-z, A-Z, 0-9, or underscore).")
        @Pattern(regexp = ".*[a-zA-Z].*", message = "Password must contain letters.")
        @Pattern(regexp = "^[a-zA-Z0-9!_$%@.-]*$", message = "Password can only contain letters, numbers, and special characters (!_$%@-).")
        @Schema(description = "The user's chosen password.", example = "myStrongPassword123!")
        String password,

        @NotNull
        @Schema(description = "The confirmation of the user's chosen password.", example = "myStrongPassword123!")
        String confirmPassword
) {
}
