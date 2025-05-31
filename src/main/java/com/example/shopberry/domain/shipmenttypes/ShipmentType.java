package com.example.shopberry.domain.shipmenttypes;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment_types")
public class ShipmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_type_id")
    private Long shipmentTypeId;

    @Column(name = "shipment_name", nullable = false, unique = true, length = 100)
    private String shipmentName;

    @Column(name = "shipment_cost", nullable = false)
    private Double shipmentCost;

}
