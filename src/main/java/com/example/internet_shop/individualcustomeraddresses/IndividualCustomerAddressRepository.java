package com.example.internet_shop.individualcustomeraddresses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerAddressRepository extends JpaRepository<IndividualCustomerAddress, Long> {
}
