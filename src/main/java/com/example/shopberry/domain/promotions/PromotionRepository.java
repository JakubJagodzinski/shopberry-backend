package com.example.shopberry.domain.promotions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    boolean existsByPromotionName(String promotionName);

    Optional<Promotion> findByPromotionName(String promotionName);

}
