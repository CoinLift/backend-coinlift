package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.users.AuthenticationResponse;
import com.coinlift.backend.dtos.users.UserAuthenticationRequest;
import com.coinlift.backend.dtos.users.UserRegistrationRequest;
import com.coinlift.backend.exceptions.ErrorDetails;
import com.coinlift.backend.services.users.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
@Tag(name = "Authentication Controller", description = "APIs related to user authentication and registration")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Register a new user.
     *
     * @param userRegistrationRequest The UserRegistrationRequest object containing user registration details.
     * @return A ResponseEntity containing an AuthenticationResponse object and HttpStatus OK if registration is successful.
     */
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Conflict - The user with the provided email or username already exists.")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        return ResponseEntity.ok(authenticationService.register(userRegistrationRequest));
    }

    /**
     * Authenticates a user based on the provided userAuthenticationRequest, generating a new JWT token upon successful authentication.
     *
     * @param userAuthenticationRequest The user authentication request containing the user's email or username and password.
     * @return ResponseEntity containing the AuthenticationResponse with the generated JWT token upon successful authentication.
     */
    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed due to invalid credentials or user not found", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(userAuthenticationRequest));
    }
}
