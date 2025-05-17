package com.example.shopberry.domain.shipmenttypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Long> {

    boolean existsByShipmentName(String shipmentName);

    ShipmentType findByShipmentName(String shipmentName);

}
