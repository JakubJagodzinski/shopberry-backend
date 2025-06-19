package com.example.shopberry.domain.cartproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

    List<CartProduct> findByCustomer_Id(Long customerId);

}
