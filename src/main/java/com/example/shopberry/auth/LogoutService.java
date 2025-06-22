package com.example.shopberry.auth;

import com.example.shopberry.auth.token.Token;
import com.example.shopberry.auth.token.TokenRepository;
import com.example.shopberry.common.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.HEADER_START)) {
            return;
        }

        final String jwt = authHeader.substring(SecurityConstants.HEADER_START.length());

        Token storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setIsExpired(true);
            storedToken.setIsRevoked(true);

            tokenRepository.save(storedToken);

            SecurityContextHolder.clearContext();
        }
    }

}
