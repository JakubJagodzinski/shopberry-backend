package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.CreateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.UpdateCustomerAddressRequestDto;
import com.example.shopberry.user.Permission;
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

    @CheckPermission(Permission.ADDRESS_READ_ALL)
    @GetMapping("/customers/addresses")
    public ResponseEntity<List<CustomerAddressResponseDto>> getAllAddresses() {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getAllAddresses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @CheckPermission(Permission.CUSTOMER_ADDRESS_READ_ALL)
    @GetMapping("/customers/{customerId}/addresses")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAllAddresses(@PathVariable UUID customerId) {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getCustomerAllAddresses(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @CheckPermission(Permission.ADDRESS_CREATE)
    @PostMapping("/customers/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponseDto> createAddress(@PathVariable UUID customerId, @Valid @RequestBody CreateCustomerAddressRequestDto createCustomerAddressRequestDto) {
        CustomerAddressResponseDto createdCustomerAddressResponseDto = customerAddressService.createAddress(customerId, createCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/addresses/" + createdCustomerAddressResponseDto.getAddressId()))
                .body(createdCustomerAddressResponseDto);
    }

    @CheckPermission(Permission.ADDRESS_UPDATE)
    @PatchMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<CustomerAddressResponseDto> updateAddressById(@PathVariable Long customerAddressId, @Valid @RequestBody UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) {
        CustomerAddressResponseDto updatedCustomerAddressResponseDto = customerAddressService.updateAddressById(customerAddressId, updateCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerAddressResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_ADDRESS_DELETE_ALL)
    @DeleteMapping("/customers/{customerId}/addresses")
    public ResponseEntity<MessageResponseDto> deleteCustomerAllAddresses(@PathVariable UUID customerId) {
        customerAddressService.deleteCustomerAllAddresses(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All customer addresses with customer id " + customerId + " deleted"));
    }

    @CheckPermission(Permission.ADDRESS_DELETE)
    @DeleteMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<MessageResponseDto> deleteAddressById(@PathVariable Long customerAddressId) {
        customerAddressService.deleteAddressById(customerAddressId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Address with id " + customerAddressId + " deleted"));
    }

}
