package com.example.shopberry.auth.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> findByUser_IdAndIsExpiredFalseOrIsRevokedFalse(Long userId);

    Optional<Token> findByToken(String token);

}
