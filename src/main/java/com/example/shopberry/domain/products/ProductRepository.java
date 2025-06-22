package com.example.shopberry.domain.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductName(String productName);

    Optional<Product> findByProductName(String productName);

    List<Product> findAllByCategory_CategoryId(Long categoryId);

}
