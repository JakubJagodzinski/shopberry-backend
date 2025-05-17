package com.example.shopberry.shipmenttypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipment_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
