package com.example.shopberry.domain.productreturns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {

    List<ProductReturn> findByOrder_OrderId(Long orderId);

    boolean existsByOrder_OrderIdAndProduct_ProductId(Long orderId, Long productId);

}
