package com.example.shopberry.domain.favouriteproducts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavouriteProductRepository extends JpaRepository<FavouriteProduct, FavouriteProductId> {

    List<FavouriteProduct> findByCustomer_UserId(UUID customerId);

}
