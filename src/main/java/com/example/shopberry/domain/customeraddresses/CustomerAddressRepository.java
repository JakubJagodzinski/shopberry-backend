package com.example.shopberry.domain.customeraddresses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    List<CustomerAddress> findAllByCustomer_CustomerId(Long customerId);

    void deleteAllByCustomer_CustomerId(Long customerId);

}
