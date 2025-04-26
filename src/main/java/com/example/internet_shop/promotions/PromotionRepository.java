package com.example.internet_shop.promotions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    boolean existsByPromotionName(String promotionName);

    Promotion findByPromotionName(String promotionName);

}
