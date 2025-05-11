package com.example.internet_shop.customers;

import com.example.internet_shop.customers.dto.CreateCustomerRequestDto;
import com.example.internet_shop.customers.dto.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping("/")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
        CustomerResponseDto createdCustomer = customerService.createCustomer(createCustomerRequestDto);
        return ResponseEntity.created(URI.create("/api/customers/" + createdCustomer.getCustomerId())).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable Long id, @RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
        return ResponseEntity.ok(customerService.updateCustomerById(id, createCustomerRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("Deleted customer with id " + id);
    }

}
