package com.example.shopberry.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public boolean isTokenValidInDatabase(String token) {
        Token databaseToken = tokenRepository.findByToken(token).orElse(null);

        if (databaseToken == null) {
            return false;
        }

        return databaseToken.getTokenType() == TokenType.ACCESS && !databaseToken.getIsExpired() && !databaseToken.getIsRevoked();
    }

}
