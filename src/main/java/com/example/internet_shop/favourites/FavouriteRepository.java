package com.example.internet_shop.favourites;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, FavouriteId> {

    List<Favourite> findByIdCustomerId(Long customerId);

    List<Favourite> findByIdProductId(Long productId);

}
