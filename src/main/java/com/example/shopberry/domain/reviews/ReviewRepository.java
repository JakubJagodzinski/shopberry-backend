package com.example.shopberry.domain.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProduct_ProductIdAndIsApprovedFalse(Long productId);

    List<Review> findAllByProduct_ProductIdAndIsApprovedTrue(Long productId);

    List<Review> findAllByProduct_ProductId(Long productId);

    List<Review> findAllByCustomer_UserId(UUID customerId);

    void deleteAllByCustomer_UserId(UUID customerId);

    void deleteAllByProduct_ProductId(Long productId);

}
