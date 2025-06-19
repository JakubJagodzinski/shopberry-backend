package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.shipmenttypes.dto.CreateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeResponseDto;
import com.example.shopberry.domain.shipmenttypes.dto.UpdateShipmentTypeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShipmentTypeController {

    private final ShipmentTypeService shipmentTypeService;

    @GetMapping("/shipment-types")
    public ResponseEntity<List<ShipmentTypeResponseDto>> getAllShipmentTypes() {
        List<ShipmentTypeResponseDto> shipmentTypeResponseDtoList = shipmentTypeService.getAllShipmentTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shipmentTypeResponseDtoList);
    }

    @GetMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<ShipmentTypeResponseDto> getShipmentTypeById(@PathVariable Long shipmentTypeId) {
        ShipmentTypeResponseDto shipmentTypeResponseDto = shipmentTypeService.getShipmentTypeById(shipmentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shipmentTypeResponseDto);
    }

    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentTypeResponseDto> createShipmentType(@RequestBody CreateShipmentTypeRequestDto createShipmentTypeRequestDto) {
        ShipmentTypeResponseDto createdShipmentTypeResponseDto = shipmentTypeService.createShipmentType(createShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/shipment-types/" + createdShipmentTypeResponseDto.getShipmentTypeId()))
                .body(createdShipmentTypeResponseDto);
    }

    @PatchMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<ShipmentTypeResponseDto> updateShipmentTypeById(@PathVariable Long shipmentTypeId, @RequestBody UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) {
        ShipmentTypeResponseDto updatedShipmentTypeResponseDto = shipmentTypeService.updateShipmentTypeById(shipmentTypeId, updateShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedShipmentTypeResponseDto);
    }

    @DeleteMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<MessageResponseDto> deleteShipmentTypeById(@PathVariable Long shipmentTypeId) {
        shipmentTypeService.deleteShipmentTypeById(shipmentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Shipment type with id " + shipmentTypeId + " deleted successfully"));
    }

}
