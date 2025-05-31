package com.example.shopberry.domain.cartentries;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartEntryRepository extends JpaRepository<CartEntry, CartEntryId> {

    List<CartEntry> findByCustomer_Id(Long customerId);

}
