package com.example.shopberry.domain.promotions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    boolean existsByPromotionName(String promotionName);

    Promotion findByPromotionName(String promotionName);

}
