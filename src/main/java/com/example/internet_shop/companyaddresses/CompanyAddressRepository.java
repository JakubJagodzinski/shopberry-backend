package com.example.internet_shop.companyaddresses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {
}
