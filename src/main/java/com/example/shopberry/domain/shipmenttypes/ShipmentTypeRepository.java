package com.example.shopberry.domain.shipmenttypes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {

    boolean existsByShipmentName(String shipmentName);

    Optional<ShipmentType> findByShipmentName(String shipmentName);

}
