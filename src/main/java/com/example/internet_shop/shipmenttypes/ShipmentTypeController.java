package com.example.internet_shop.shipmenttypes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shipment_types")
public class ShipmentTypeController {

    private final ShipmentTypeService shipmentTypeService;

    public ShipmentTypeController(ShipmentTypeService shipmentTypeService) {
        this.shipmentTypeService = shipmentTypeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ShipmentTypeDto>> getShipmentTypes() {
        return ResponseEntity.ok(shipmentTypeService.getShipmentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentTypeDto> getShipmentTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentTypeService.getShipmentTypeById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ShipmentTypeDto> createShipmentType(@RequestBody CreateShipmentTypeDto createShipmentTypeDto) {
        ShipmentTypeDto createdShipmentType = shipmentTypeService.createShipmentType(createShipmentTypeDto);

        return ResponseEntity.created(URI.create("/api/shipment_types/" + createdShipmentType.getShipmentTypeId())).body(createdShipmentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentTypeDto> updateShipmentTypeById(@PathVariable Long id, @RequestBody UpdateShipmentTypeDto updateShipmentTypeDto) {
        return ResponseEntity.ok(shipmentTypeService.updateShipmentTypeById(id, updateShipmentTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipmentTypeById(@PathVariable Long id) {
        shipmentTypeService.deleteShipmentTypeById(id);

        return ResponseEntity.ok("Deleted shipment type with id: " + id);
    }

}
