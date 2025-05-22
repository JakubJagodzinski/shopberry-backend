package com.example.shopberry.auth;

import com.example.shopberry.auth.dto.AuthenticationRequestDto;
import com.example.shopberry.auth.dto.AuthenticationResponseDto;
import com.example.shopberry.auth.dto.RegisterRequestDto;
import com.example.shopberry.auth.jwt.JwtService;
import com.example.shopberry.auth.jwt.Token;
import com.example.shopberry.auth.jwt.TokenRepository;
import com.example.shopberry.auth.jwt.TokenType;
import com.example.shopberry.user.User;
import com.example.shopberry.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final String USER_NOT_FOUND_MESSAGE = "User not found";
    private final String USER_ALREADY_EXISTS_MESSAGE = "User already exists";

    public AuthenticationResponseDto register(RegisterRequestDto request) throws IllegalArgumentException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException(USER_ALREADY_EXISTS_MESSAGE);
        }

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws EntityNotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findByUser_IdAndIsExpiredFalseOrIsRevokedFalse(user.getId());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setIsExpired(true);
            token.setIsRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        final String headerStart = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(headerStart)) {
            return;
        }

        refreshToken = authHeader.substring(headerStart.length());

        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                revokeAllUserTokens(user);

                saveUserToken(user, accessToken);

                AuthenticationResponseDto authResponse = AuthenticationResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
