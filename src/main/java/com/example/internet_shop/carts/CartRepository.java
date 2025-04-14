package com.example.internet_shop.carts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartId> {

    List<Cart> findByIdOrderId(Long orderId);

    List<Cart> findByIdProductId(Long productId);

}
