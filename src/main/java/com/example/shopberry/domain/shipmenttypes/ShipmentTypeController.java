package com.example.shopberry.domain.shipmenttypes;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.shipmenttypes.dto.request.CreateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.request.UpdateShipmentTypeRequestDto;
import com.example.shopberry.domain.shipmenttypes.dto.response.ShipmentTypeResponseDto;
import com.example.shopberry.exception.ApiError;
import com.example.shopberry.user.Permission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all shipment types")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of shipment types",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipmentTypeResponseDto.class)
                    )
            )
    })
    @GetMapping("/shipment-types")
    public ResponseEntity<List<ShipmentTypeResponseDto>> getAllShipmentTypes() {
        List<ShipmentTypeResponseDto> shipmentTypeResponseDtoList = shipmentTypeService.getAllShipmentTypes();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shipmentTypeResponseDtoList);
    }

    @Operation(summary = "Get shipment type by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Shipment type found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipmentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shipment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<ShipmentTypeResponseDto> getShipmentTypeById(@PathVariable Long shipmentTypeId) {
        ShipmentTypeResponseDto shipmentTypeResponseDto = shipmentTypeService.getShipmentTypeById(shipmentTypeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(shipmentTypeResponseDto);
    }

    @Operation(summary = "Create a new shipment type")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Shipment type created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipmentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Shipment type with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.SHIPMENT_TYPE_CREATE)
    @PostMapping("/shipment-types")
    public ResponseEntity<ShipmentTypeResponseDto> createShipmentType(@Valid @RequestBody CreateShipmentTypeRequestDto createShipmentTypeRequestDto) {
        ShipmentTypeResponseDto createdShipmentTypeResponseDto = shipmentTypeService.createShipmentType(createShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/shipment-types/" + createdShipmentTypeResponseDto.getShipmentTypeId()))
                .body(createdShipmentTypeResponseDto);
    }

    @Operation(summary = "Update shipment type by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Shipment type updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipmentTypeResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Shipment type with that name already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shipment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.SHIPMENT_TYPE_UPDATE)
    @PatchMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<ShipmentTypeResponseDto> updateShipmentTypeById(@PathVariable Long shipmentTypeId, @Valid @RequestBody UpdateShipmentTypeRequestDto updateShipmentTypeRequestDto) {
        ShipmentTypeResponseDto updatedShipmentTypeResponseDto = shipmentTypeService.updateShipmentTypeById(shipmentTypeId, updateShipmentTypeRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedShipmentTypeResponseDto);
    }

    @Operation(summary = "Delete shipment type by ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Shipment type deleted"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Shipment type not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.SHIPMENT_TYPE_DELETE)
    @DeleteMapping("/shipment-types/{shipmentTypeId}")
    public ResponseEntity<Void> deleteShipmentTypeById(@PathVariable Long shipmentTypeId) {
        shipmentTypeService.deleteShipmentTypeById(shipmentTypeId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
