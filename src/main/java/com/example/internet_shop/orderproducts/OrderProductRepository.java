package com.example.internet_shop.orderproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

    List<OrderProduct> findByIdOrderId(Long orderId);

    List<OrderProduct> findByIdProductId(Long productId);

}
