package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.CreateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.UpdateCustomerAddressRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @CheckPermission(Permission.CUSTOMER_ADDRESS_READ_ALL)
    @GetMapping("/customers/addresses")
    public ResponseEntity<List<CustomerAddressResponseDto>> getAllCustomerAddresses() {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getAllCustomerAddresses();

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

    @CheckPermission(Permission.CUSTOMER_ADDRESS_CREATE)
    @PostMapping("/customers/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponseDto> createCustomerAddress(@PathVariable UUID customerId, @RequestBody CreateCustomerAddressRequestDto createCustomerAddressRequestDto) {
        CustomerAddressResponseDto createdCustomerAddressResponseDto = customerAddressService.createCustomerAddress(customerId, createCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/addresses/" + createdCustomerAddressResponseDto.getAddressId()))
                .body(createdCustomerAddressResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_ADDRESS_UPDATE)
    @PatchMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<CustomerAddressResponseDto> updateCustomerAddressById(@PathVariable Long customerAddressId, @RequestBody UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) {
        CustomerAddressResponseDto updatedCustomerAddressResponseDto = customerAddressService.updateCustomerAddressById(customerAddressId, updateCustomerAddressRequestDto);

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

    @CheckPermission(Permission.CUSTOMER_ADDRESS_DELETE)
    @DeleteMapping("/customers/addresses/{customerAddressId}")
    public ResponseEntity<MessageResponseDto> deleteCustomerAddressById(@PathVariable Long customerAddressId) {
        customerAddressService.deleteCustomerAddressById(customerAddressId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Customer address with id " + customerAddressId + " deleted"));
    }

}
