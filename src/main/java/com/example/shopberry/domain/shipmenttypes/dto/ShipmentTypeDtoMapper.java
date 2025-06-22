package com.example.shopberry.domain.shipmenttypes.dto;

import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShipmentTypeDtoMapper {

    public ShipmentTypeResponseDto toDto(ShipmentType shipmentType) {
        if (shipmentType == null) {
            return null;
        }

        ShipmentTypeResponseDto ShipmentTypeResponseDto = new ShipmentTypeResponseDto();

        ShipmentTypeResponseDto.setShipmentTypeId(shipmentType.getShipmentTypeId());
        ShipmentTypeResponseDto.setShipmentName(shipmentType.getShipmentName());
        ShipmentTypeResponseDto.setShipmentCost(shipmentType.getShipmentCost());

        return ShipmentTypeResponseDto;
    }

    public List<ShipmentTypeResponseDto> toDtoList(List<ShipmentType> shipmentTypeList) {
        return shipmentTypeList.stream()
                .map(this::toDto)
                .toList();
    }

}
