package com.coinlift.backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Objects;

@Builder
public class UserAuthenticationRequest {

    @Schema(description = "The user's email address or username.", example = "john.doe@example.com or john_doe")
    private String emailOrUsername;

    @Schema(description = "The user's password.", example = "myStrongPassword123!")
    private String password;

    public UserAuthenticationRequest(String emailOrUsername, String password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    public UserAuthenticationRequest() {
    }

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public void setEmailOrUsername(String emailOrUsername) {
        this.emailOrUsername = emailOrUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthenticationRequest{" +
                "email='" + emailOrUsername + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthenticationRequest that = (UserAuthenticationRequest) o;
        return Objects.equals(emailOrUsername, that.emailOrUsername) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailOrUsername, password);
    }
}
