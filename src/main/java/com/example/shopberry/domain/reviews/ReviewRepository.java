package com.example.shopberry.domain.reviews;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct_ProductId(Long productId);

    List<Review> findByCustomer_Id(Long customerId);

    void deleteByCustomer_Id(Long customerId);

    void deleteByProduct_ProductId(Long productId);

}
