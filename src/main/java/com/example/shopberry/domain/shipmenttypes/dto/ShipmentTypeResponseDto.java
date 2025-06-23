package com.example.shopberry.domain.shipmenttypes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"shipment_type_id", "shipment_name", "shipment_cost"})
public class ShipmentTypeResponseDto {

    @JsonProperty("shipment_type_id")
    private Long shipmentTypeId;

    @JsonProperty("shipment_name")
    private String shipmentName;

    @JsonProperty("shipment_cost")
    private Double shipmentCost;

}
