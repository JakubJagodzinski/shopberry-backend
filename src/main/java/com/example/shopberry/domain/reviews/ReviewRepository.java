package com.example.shopberry.domain.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct_ProductId(Long productId);

    List<Review> findByCustomer_UserId(UUID customerId);

    void deleteByCustomer_UserId(UUID customerId);

    void deleteByProduct_ProductId(Long productId);

}
