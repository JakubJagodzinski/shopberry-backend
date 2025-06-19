package com.example.shopberry.domain.productreturns;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReturnRepository extends JpaRepository<ProductReturn, Long> {

    List<ProductReturn> findAllByOrder_OrderId(Long orderId);

    boolean existsByOrder_OrderIdAndProduct_ProductId(Long orderId, Long productId);

}
