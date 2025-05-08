package com.example.internet_shop.shipmenttypes;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipmentTypeDtoMapper {

    public ShipmentTypeDto toDto(ShipmentType shipmentType) {
        ShipmentTypeDto ShipmentTypeDto = new ShipmentTypeDto();

        ShipmentTypeDto.setShipmentTypeId(shipmentType.getShipmentTypeId());
        ShipmentTypeDto.setShipmentName(shipmentType.getShipmentName());
        ShipmentTypeDto.setShipmentCost(shipmentType.getShipmentCost());

        return ShipmentTypeDto;
    }

    public List<ShipmentTypeDto> toDtoList(List<ShipmentType> shipmentTypes) {
        return shipmentTypes.stream()
                .map(this::toDto)
                .toList();
    }

}
