package com.example.internet_shop.customeraddresses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer_addresses")
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    public CustomerAddressController(CustomerAddressService customerAddressService) {
        this.customerAddressService = customerAddressService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerAddressDto>> getCustomerAddresses() {
        return ResponseEntity.ok(customerAddressService.getCustomerAddresses());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CustomerAddressDto>> getCustomerAddressesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerAddressService.getCustomerAddressesByCustomerId(customerId));
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<CustomerAddressDto> createCustomerAddress(@PathVariable Long customerId, @RequestBody CreateCustomerAddressDto createCustomerAddressDto) {
        CustomerAddressDto createdCustomerAddress = customerAddressService.createCustomerAddress(customerId, createCustomerAddressDto);

        return ResponseEntity.created(URI.create("/api/customer_addresses/" + createdCustomerAddress.getAddressId())).body(createdCustomerAddress);
    }

    @PutMapping("/{customerAddressId}")
    public ResponseEntity<CustomerAddressDto> updateCustomerAddressById(@PathVariable Long customerAddressId, @RequestBody UpdateCustomerAddressDto updateCustomerAddressDto) {
        return ResponseEntity.ok(customerAddressService.updateCustomerAddressById(customerAddressId, updateCustomerAddressDto));
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
