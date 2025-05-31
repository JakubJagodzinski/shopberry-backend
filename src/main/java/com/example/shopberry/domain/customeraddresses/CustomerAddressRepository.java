package com.example.shopberry.domain.customeraddresses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    List<CustomerAddress> findAllByCustomer_Id(Long customerId);

    void deleteAllByCustomer_Id(Long customerId);

}
