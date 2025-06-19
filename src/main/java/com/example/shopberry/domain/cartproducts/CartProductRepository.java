package com.example.shopberry.domain.cartproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

    List<CartProduct> findAllByCustomer_UserId(UUID customerId);

}
