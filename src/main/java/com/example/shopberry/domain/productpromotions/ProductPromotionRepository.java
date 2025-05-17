package com.example.shopberry.domain.productpromotions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPromotionRepository extends JpaRepository<ProductPromotion, ProductPromotionId> {

    List<ProductPromotion> findById_ProductId(Long productId);

    List<ProductPromotion> findById_PromotionId(Long promotionId);

    void deleteById_ProductId(Long productId);

    void deleteById_PromotionId(Long promotionId);

}
