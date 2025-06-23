package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.customeraddresses.dto.request.CreateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.request.UpdateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.response.CustomerAddressResponseDto;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @Operation(summary = "Get all addresses from database")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of addresses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerAddressResponseDto.class)
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
    @CheckPermission(Permission.ADDRESS_READ_ALL)
    @GetMapping("/customers/addresses")
    public ResponseEntity<List<CustomerAddressResponseDto>> getAllAddresses() {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getAllAddresses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @Operation(summary = "Get customer all addresses")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customer addresses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerAddressResponseDto.class)
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
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_ADDRESS_READ_ALL)
    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAllAddresses(@PathVariable UUID customerId) {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getCustomerAllAddresses(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @Operation(summary = "Create new customer address")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer address created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerAddressResponseDto.class)
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
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ADDRESS_CREATE)
    @PostMapping("/customers/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponseDto> createAddress(@PathVariable UUID customerId, @Valid @RequestBody CreateCustomerAddressRequestDto createCustomerAddressRequestDto) {
        CustomerAddressResponseDto createdCustomerAddressResponseDto = customerAddressService.createAddress(customerId, createCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/addresses/" + createdCustomerAddressResponseDto.getAddressId()))
                .body(createdCustomerAddressResponseDto);
    }

    @Operation(summary = "Update customer address")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Updated customer address",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerAddressResponseDto.class)
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
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ADDRESS_UPDATE)
    @PatchMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<CustomerAddressResponseDto> updateAddressById(@PathVariable Long customerAddressId, @Valid @RequestBody UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) {
        CustomerAddressResponseDto updatedCustomerAddressResponseDto = customerAddressService.updateAddressById(customerAddressId, updateCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerAddressResponseDto);
    }

    @Operation(summary = "Delete all customer's addresses")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "All customer addresses have been deleted successfully"
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
                    description = "Customer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_ADDRESS_DELETE_ALL)
    @DeleteMapping("/customers/{customerId}/addresses")
    public ResponseEntity<Void> deleteCustomerAllAddresses(@PathVariable UUID customerId) {
        customerAddressService.deleteCustomerAllAddresses(customerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "Delete address by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Address deleted successfully"
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
                    description = "Address not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.ADDRESS_DELETE)
    @DeleteMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long customerAddressId) {
        customerAddressService.deleteAddressById(customerAddressId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
