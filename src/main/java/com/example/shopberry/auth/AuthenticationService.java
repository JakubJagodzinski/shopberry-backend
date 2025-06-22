package com.example.shopberry.auth;

import com.example.shopberry.auth.dto.*;
import com.example.shopberry.auth.jwt.JwtService;
import com.example.shopberry.auth.token.Token;
import com.example.shopberry.auth.token.TokenRepository;
import com.example.shopberry.auth.token.TokenType;
import com.example.shopberry.common.constants.messages.TokenMessages;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.employees.Employee;
import com.example.shopberry.user.Role;
import com.example.shopberry.user.User;
import com.example.shopberry.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private AuthenticationResponseDto generateUserToken(User user) {
        String jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken, TokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public void register(RegisterRequestDto registerRequestDto) throws IllegalArgumentException {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new IllegalArgumentException(UserMessages.EMAIL_IS_ALREADY_TAKEN);
        }

        Role userRole = parseRole(registerRequestDto.getRole());
        User user = createUserInstance(userRole);

        populateCommonUserFields(user, registerRequestDto);

        userRepository.save(user);
    }

    private Role parseRole(String role) throws IllegalArgumentException {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        }
    }

    private User createUserInstance(Role role) throws IllegalArgumentException {
        return switch (role) {
            case CUSTOMER -> new Customer();
            case EMPLOYEE -> new Employee();
            default -> throw new IllegalArgumentException(UserMessages.INVALID_USER_ROLE);
        };
    }

    private void populateCommonUserFields(User user, RegisterRequestDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws AccessDeniedException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            throw new AccessDeniedException(UserMessages.WRONG_USERNAME_OR_PASSWORD);
        }

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null) {
            throw new AccessDeniedException(UserMessages.WRONG_USERNAME_OR_PASSWORD);
        }

        revokeAllUserTokens(user);

        return generateUserToken(user);
    }

    public void saveUserToken(User user, String tokenValue, TokenType tokenType) {
        Token token = new Token();

        token.setToken(tokenValue);
        token.setTokenType(tokenType);
        token.setUser(user);

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllByUser_UserIdAndIsExpiredFalseOrIsRevokedFalse(user.getUserId());

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

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto requestDto) throws IllegalArgumentException {
        String refreshToken = requestDto.getRefreshToken();

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        User user = userRepository.findByEmail(userEmail).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        Token token = tokenRepository.findByToken(refreshToken).orElse(null);

        if (token == null) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (token.getTokenType() != TokenType.REFRESH) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        if (!jwtService.isTokenValid(refreshToken, user) || token.getIsExpired() || token.getIsRevoked()) {
            throw new IllegalArgumentException(TokenMessages.PROVIDED_REFRESH_TOKEN_IS_INVALID_OR_EXPIRED);
        }

        revokeAllUserTokens(user);

        String newAccessToken = jwtService.generateToken(user);
        saveUserToken(user, newAccessToken, TokenType.ACCESS);

        String newRefreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, newRefreshToken, TokenType.REFRESH);

        return RefreshTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
