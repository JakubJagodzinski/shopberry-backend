package com.example.shopberry.domain.favouriteentries;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteEntryRepository extends JpaRepository<FavouriteEntry, FavouriteEntryId> {

    List<FavouriteEntry> findById_CustomerId(Long customerId);

}
