package com.example.shopberry.domain.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct_ProductId(Long productId);

    List<Review> findByCustomer_Id(Long customerId);

    void deleteByCustomer_Id(Long customerId);

    void deleteByProduct_ProductId(Long productId);

}
