package com.example.internet_shop.shipmenttypes;

import com.example.internet_shop.shipmenttypes.dto.CreateShipmentTypeRequestDto;
import com.example.internet_shop.shipmenttypes.dto.ShipmentTypeResponseDto;
import com.example.internet_shop.shipmenttypes.dto.UpdateShipmentTypeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipment-types")
public class ShipmentTypeController {

    private final ShipmentTypeService shipmentTypeService;

    public ShipmentTypeController(ShipmentTypeService shipmentTypeService) {
        this.shipmentTypeService = shipmentTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ShipmentTypeResponseDto>> getShipmentTypes() {
        return ResponseEntity.ok(shipmentTypeService.getShipmentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentTypeResponseDto> getShipmentTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentTypeService.getShipmentTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ShipmentTypeResponseDto> createShipmentType(@RequestBody CreateShipmentTypeRequestDto createShipmentTypeRequestDto) {
        ShipmentTypeResponseDto createdShipmentType = shipmentTypeService.createShipmentType(createShipmentTypeRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/shipment-types/" + createdShipmentType.getShipmentTypeId())).body(createdShipmentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentTypeResponseDto> updateShipmentTypeById(@PathVariable Long id, @RequestBody UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) {
        return ResponseEntity.ok(shipmentTypeService.updateShipmentTypeById(id, updateShipmentTypeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipmentTypeById(@PathVariable Long id) {
        shipmentTypeService.deleteShipmentTypeById(id);

        return ResponseEntity.ok("Deleted shipment type with id: " + id);
    }

}
