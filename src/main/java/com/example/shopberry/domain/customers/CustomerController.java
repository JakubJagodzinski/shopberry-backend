package com.example.shopberry.domain.customers;

import com.example.shopberry.domain.customers.dto.CreateCustomerRequestDto;
import com.example.shopberry.domain.customers.dto.CustomerResponseDto;
import com.example.shopberry.domain.customers.dto.UpdateCustomerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<CustomerResponseDto> customerResponseDtoList = customerService.getCustomers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        CustomerResponseDto customerResponseDto = customerService.getCustomerById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDto);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CreateCustomerRequestDto createCustomerRequestDto) {
        CustomerResponseDto createdCustomerResponseDto = customerService.createCustomer(createCustomerRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + createdCustomerResponseDto.getCustomerId()))
                .body(createdCustomerResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable Long id, @RequestBody UpdateCustomerRequestDto updateCustomerRequestDto) {
        CustomerResponseDto updatedCustomerResponseDto = customerService.updateCustomerById(id, updateCustomerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Customer with id " + id + " deleted successfully");
    }

}
