package com.example.shopberry.auth;

import com.example.shopberry.auth.dto.AuthenticationRequestDto;
import com.example.shopberry.auth.dto.AuthenticationResponseDto;
import com.example.shopberry.auth.dto.RefreshTokenRequestDto;
import com.example.shopberry.auth.dto.RegisterRequestDto;
import com.example.shopberry.auth.jwt.JwtService;
import com.example.shopberry.auth.jwt.Token;
import com.example.shopberry.auth.jwt.TokenRepository;
import com.example.shopberry.auth.jwt.TokenType;
import com.example.shopberry.domain.customers.CustomerService;
import com.example.shopberry.domain.employees.EmployeeService;
import com.example.shopberry.user.Role;
import com.example.shopberry.user.User;
import com.example.shopberry.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";
    private static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists";
    private static final String INVALID_ROLE_MESSAGE = "Invalid role specified";
    private static final String REFRESH_TOKEN_IS_MISSING_MESSAGE = "Refresh token is missing";
    private static final String INVALID_TOKEN_NO_SUBJECT_MESSAGE = "Invalid token: no subject";
    private static final String TOKEN_NOT_FOUND_MESSAGE = "Token not found";
    private static final String PROVIDED_TOKEN_IS_NOT_A_REFRESH_TOKEN_MESSAGE = "Provided token is not a refresh token";
    private static final String REFRESH_TOKEN_IS_INVALID_OR_EXPIRED_MESSAGE = "Refresh token is invalid or expired";

    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) throws IllegalArgumentException {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new IllegalArgumentException(USER_ALREADY_EXISTS_MESSAGE);
        }

        User user;

        if (registerRequestDto.getRole().equals(Role.CUSTOMER)) {
            user = customerService.register(registerRequestDto);
        } else if (registerRequestDto.getRole().equals(Role.EMPLOYEE)) {
            user = employeeService.register(registerRequestDto);
        } else {
            throw new IllegalArgumentException(INVALID_ROLE_MESSAGE);
        }

        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken, TokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
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

        revokeAllUserTokens(user);

        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken, TokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public void saveUserToken(User user, String tokenValue, TokenType tokenType) {
        Token token = new Token();

        token.setToken(tokenValue);
        token.setTokenType(tokenType);
        token.setUser(user);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findByUser_IdAndIsExpiredFalseOrIsRevokedFalse(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(
                token -> {
                    token.setIsExpired(true);
                    token.setIsRevoked(true);
                }
        );

        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto requestDto) throws IllegalArgumentException, UsernameNotFoundException {
        String refreshToken = requestDto.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException(REFRESH_TOKEN_IS_MISSING_MESSAGE);
        }

        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new IllegalArgumentException(INVALID_TOKEN_NO_SUBJECT_MESSAGE);
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        Token token = tokenRepository.findByToken(refreshToken).orElse(null);

        if (token == null) {
            throw new IllegalArgumentException(TOKEN_NOT_FOUND_MESSAGE);
        }

        if (token.getTokenType() != TokenType.REFRESH) {
            throw new IllegalArgumentException(PROVIDED_TOKEN_IS_NOT_A_REFRESH_TOKEN_MESSAGE);
        }

        if (!jwtService.isTokenValid(refreshToken, user) || token.getIsExpired() || token.getIsRevoked()) {
            throw new IllegalArgumentException(REFRESH_TOKEN_IS_INVALID_OR_EXPIRED_MESSAGE);
        }

        revokeAllUserTokens(user);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(user, newAccessToken, TokenType.ACCESS);
        saveUserToken(user, newRefreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
