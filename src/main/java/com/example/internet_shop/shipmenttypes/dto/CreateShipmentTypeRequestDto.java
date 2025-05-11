package com.example.internet_shop.shipmenttypes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShipmentTypeRequestDto {

    private String shipmentName;

    private Double shipmentCost;

}
