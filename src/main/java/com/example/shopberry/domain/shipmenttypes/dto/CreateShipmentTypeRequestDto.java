package com.example.shopberry.domain.shipmenttypes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentTypeRequestDto {

    @JsonProperty("shipment_name")
    private String shipmentName;

    @JsonProperty("shipment_cost")
    private Double shipmentCost;

}
