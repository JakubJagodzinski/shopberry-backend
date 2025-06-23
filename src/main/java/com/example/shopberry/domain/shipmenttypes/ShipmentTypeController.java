package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.shipmenttypes.dto.request.CreateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.request.UpdateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.response.ShipmentTypeResponseDto;
import com.example.shopberry.user.Permission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
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

    @CheckPermission(Permission.SHIPMENT_TYPE_CREATE)
    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentTypeResponseDto> createShipmentType(@Valid @RequestBody CreateShipmentTypeRequestDto createShipmentTypeRequestDto) {
        ShipmentTypeResponseDto createdShipmentTypeResponseDto = shipmentTypeService.createShipmentType(createShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/shipment-types/" + createdShipmentTypeResponseDto.getShipmentTypeId()))
                .body(createdShipmentTypeResponseDto);
    }

    @CheckPermission(Permission.SHIPMENT_TYPE_UPDATE)
    @PatchMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<ShipmentTypeResponseDto> updateShipmentTypeById(@PathVariable Long shipmentTypeId, @Valid @RequestBody UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) {
        ShipmentTypeResponseDto updatedShipmentTypeResponseDto = shipmentTypeService.updateShipmentTypeById(shipmentTypeId, updateShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedShipmentTypeResponseDto);
    }

    @CheckPermission(Permission.SHIPMENT_TYPE_DELETE)
    @DeleteMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<Void> deleteShipmentTypeById(@PathVariable Long shipmentTypeId) {
        shipmentTypeService.deleteShipmentTypeById(shipmentTypeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
