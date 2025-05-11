package com.example.internet_shop.customeraddresses;

import com.example.internet_shop.customeraddresses.dto.CreateCustomerAddressRequestDto;
import com.example.internet_shop.customeraddresses.dto.CustomerAddressResponseDto;
import com.example.internet_shop.customeraddresses.dto.UpdateCustomerAddressRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-addresses")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAddresses() {
        return ResponseEntity.ok(customerAddressService.getCustomerAddresses());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerAddressResponseDto>> getCustomerAddressesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerAddressService.getCustomerAddressesByCustomerId(customerId));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CustomerAddressResponseDto> createCustomerAddress(@PathVariable Long customerId, @RequestBody CreateCustomerAddressRequestDto createCustomerAddressRequestDto) {
        CustomerAddressResponseDto createdCustomerAddress = customerAddressService.createCustomerAddress(customerId, createCustomerAddressRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/customer-addresses/" + createdCustomerAddress.getAddressId())).body(createdCustomerAddress);
    }

    @PutMapping("/{customerAddressId}")
    public ResponseEntity<CustomerAddressResponseDto> updateCustomerAddressById(@PathVariable Long customerAddressId, @RequestBody UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) {
        return ResponseEntity.ok(customerAddressService.updateCustomerAddressById(customerAddressId, updateCustomerAddressRequestDto));
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomerAddressesByCustomerId(@PathVariable Long customerId) {
        customerAddressService.deleteCustomerAddressesByCustomerId(customerId);

        return ResponseEntity.ok("Deleted customer addresses with customer id " + customerId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerAddressById(@PathVariable Long id) {
        customerAddressService.deleteCustomerAddressById(id);

        return ResponseEntity.ok("Deleted customer address with id " + id);
    }

}
