package com.example.shopberry.auth;

import com.example.shopberry.auth.jwt.Token;
import com.example.shopberry.auth.jwt.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String headerStart = "Bearer ";

        if (authHeader == null || !authHeader.startsWith(headerStart)) {
            return;
        }

        final String jwt = authHeader.substring(headerStart.length());

        Token storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setIsExpired(true);
            storedToken.setIsRevoked(true);

            tokenRepository.save(storedToken);

            SecurityContextHolder.clearContext();
        }
    }

}
