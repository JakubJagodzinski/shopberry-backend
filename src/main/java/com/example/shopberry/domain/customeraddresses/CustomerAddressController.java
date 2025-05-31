package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.CreateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.UpdateCustomerAddressRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @GetMapping("/")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAddresses() {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getCustomerAddresses();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAddressesByCustomerId(@PathVariable Long customerId) {
        List<CustomerAddressResponseDto> customerAddressResponseDtoList = customerAddressService.getCustomerAddressesByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerAddressResponseDtoList);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CustomerAddressResponseDto> createCustomerAddress(@PathVariable Long customerId, @RequestBody CreateCustomerAddressRequestDto createCustomerAddressRequestDto) {
        CustomerAddressResponseDto createdCustomerAddressResponseDto = customerAddressService.createCustomerAddress(customerId, createCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customer-addresses/" + createdCustomerAddressResponseDto.getAddressId()))
                .body(createdCustomerAddressResponseDto);
    }

    @PutMapping("/{customerAddressId}")
    public ResponseEntity<CustomerAddressResponseDto> updateCustomerAddressById(@PathVariable Long customerAddressId, @RequestBody UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) {
        CustomerAddressResponseDto updatedCustomerAddressResponseDto = customerAddressService.updateCustomerAddressById(customerAddressId, updateCustomerAddressRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerAddressResponseDto);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<MessageResponseDto> deleteCustomerAddressesByCustomerId(@PathVariable Long customerId) {
        customerAddressService.deleteCustomerAddressesByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("All customer addresses with customer id " + customerId + " deleted"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteCustomerAddressById(@PathVariable Long id) {
        customerAddressService.deleteCustomerAddressById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Customer address with id " + id + " deleted"));
    }

}
