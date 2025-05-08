package com.example.internet_shop.cartentries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartEntryRepository extends JpaRepository<CartEntry, CartEntryId> {

    List<CartEntry> findByCustomer_CustomerId(Long customerId);

}
