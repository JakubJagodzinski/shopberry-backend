package com.example.shopberry.domain.favouriteproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteProductRepository extends JpaRepository<FavouriteProduct, FavouriteProductId> {

    List<FavouriteProduct> findById_CustomerId(Long customerId);

}
