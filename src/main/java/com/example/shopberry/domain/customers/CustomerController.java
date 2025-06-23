package com.example.shopberry.domain.customers;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.customers.dto.request.UpdateCustomerRequestDto;
import com.example.shopberry.domain.customers.dto.response.CustomerResponseDto;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get all customers")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of customers",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_READ_ALL)
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customerResponseDtoList = customerService.getAllCustomers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDtoList);
    }

    @Operation(summary = "Get customer by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class)
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
                    description = "Customer not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_READ)
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID customerId) {
        CustomerResponseDto customerResponseDto = customerService.getCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDto);
    }

    @Operation(summary = "Update customer by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class)
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
                    description = "Customer not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_UPDATE)
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable UUID customerId, @Valid @RequestBody UpdateCustomerRequestDto updateCustomerRequestDto) {
        CustomerResponseDto updatedCustomerResponseDto = customerService.updateCustomerById(customerId, updateCustomerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerResponseDto);
    }

    @Operation(summary = "Delete customer by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Customer deleted successfully"
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
                    description = "Customer not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @CheckPermission(Permission.CUSTOMER_DELETE)
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable UUID customerId) {
        customerService.deleteCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
