package com.example.shopberry.domain.productpromotions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, ProductPromotionId> {

    List<ProductPromotion> findAllByProduct_ProductId(Long productId);

    List<ProductPromotion> findAllByPromotion_PromotionId(Long promotionId);

    void deleteAllByProduct_ProductId(Long productId);

    void deleteAllByPromotion_PromotionId(Long promotionId);

}
