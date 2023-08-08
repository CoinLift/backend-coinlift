package com.coinlift.backend.services.users;

import com.coinlift.backend.dtos.users.AuthenticationResponse;
import com.coinlift.backend.dtos.users.UserAuthenticationRequest;
import com.coinlift.backend.dtos.users.UserRegistrationRequest;
import com.coinlift.backend.entities.user.*;
import com.coinlift.backend.exceptions.DuplicateUserException;
import com.coinlift.backend.exceptions.PasswordMismatchException;
import com.coinlift.backend.repositories.TokenRepository;
import com.coinlift.backend.repositories.UserRepository;
import com.coinlift.backend.services.users.security.JwtService;
import com.coinlift.backend.services.users.security.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, JwtService jwtService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Registers a new user in the system by validating the password and generating a JWT token upon successful registration.
     *
     * @param userRegistrationRequest The user registration request containing user details.
     * @return An AuthenticationResponse containing the generated JWT token upon successful registration.
     */
    @Override
    public AuthenticationResponse register(UserRegistrationRequest userRegistrationRequest) {
        String pass = userRegistrationRequest.password();
        String confirmPass = userRegistrationRequest.confirmPassword();
        String emailAddress = userRegistrationRequest.emailAddress();
        String username = userRegistrationRequest.username();

        checkEmailAndUsernameUniqueness(emailAddress, username);
        validatePasswordConfirmation(pass, confirmPass);

        User user = User.builder()
                .username(username.toLowerCase())
                .email(emailAddress.toLowerCase())
                .password(passwordEncoder.encode(pass))
                .role(Role.USER)
                .imageUrl("af5be274-71e7-4561-98fd-b33f80f759cf")
                .followersCount(0)
                .followingCount(0)
                .build();

        User savedUser = userRepository.save(user);
        MyUserDetails userDetails = new MyUserDetails(user);

        String jwtToken = jwtService.generateToken(userDetails);
        revokeUserTokens(savedUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user based on the provided email or username and password, generating a new JWT token upon successful authentication.
     *
     * @param userAuthenticationRequest The user authentication request containing the user's email or username and password.
     * @return An AuthenticationResponse containing the generated JWT token upon successful authentication.
     * @throws org.springframework.security.authentication.BadCredentialsException if the provided email/username and password combination is invalid.
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException if the user with the provided email or username is not found in the database.
     */
    @Override
    public AuthenticationResponse authenticate(UserAuthenticationRequest userAuthenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userAuthenticationRequest.getEmailOrUsername(),
                userAuthenticationRequest.getPassword()
        ));

        MyUserDetails userDetails = userDetailsService.loadUserByUsername(userAuthenticationRequest.getEmailOrUsername());
        String jwtToken = jwtService.generateToken(userDetails);
        revokeUserTokens(userDetails.user());
        saveUserToken(userDetails.user(), jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void validatePasswordConfirmation(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Password and confirm password don't match.");
        }
    }

    private void checkEmailAndUsernameUniqueness(String email, String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicateUserException("User with this username already exists.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateUserException("User with this email already exists.");
        }
    }

    private void revokeUserTokens(User user) {
        List<AuthenticationToken> validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = AuthenticationToken.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
}
