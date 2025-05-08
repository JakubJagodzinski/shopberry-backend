package com.example.internet_shop.favouriteentries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteEntryRepository extends JpaRepository<FavouriteEntry, FavouriteEntryId> {

    List<FavouriteEntry> findById_CustomerId(Long customerId);

}
