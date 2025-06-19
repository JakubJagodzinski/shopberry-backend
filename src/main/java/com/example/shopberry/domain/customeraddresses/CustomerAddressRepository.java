package com.example.shopberry.domain.customeraddresses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    List<CustomerAddress> findAllByCustomer_UserId(UUID customerId);

    void deleteAllByCustomer_UserId(UUID customerId);

}
