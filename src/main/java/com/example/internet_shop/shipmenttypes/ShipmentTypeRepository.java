package com.example.internet_shop.shipmenttypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {
}
