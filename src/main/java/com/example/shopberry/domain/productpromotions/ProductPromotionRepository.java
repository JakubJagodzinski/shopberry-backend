package com.example.shopberry.domain.productpromotions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, ProductPromotionId> {

    List<ProductPromotion> findByProduct_ProductId(Long productId);

    List<ProductPromotion> findByPromotion_PromotionId(Long promotionId);

    void deleteByProduct_ProductId(Long productId);

    void deleteByPromotion_PromotionId(Long promotionId);

}
