package com.example.shopberry.auth.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findAllByUser_UserIdAndIsExpiredFalseOrIsRevokedFalse(UUID userId);

    Optional<Token> findByToken(String token);

}
