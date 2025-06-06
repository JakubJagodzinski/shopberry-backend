package com.example.shopberry.domain.customers;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.customers.dto.CustomerResponseDto;
import com.example.shopberry.domain.customers.dto.UpdateCustomerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<CustomerResponseDto> customerResponseDtoList = customerService.getCustomers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDtoList);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long customerId) {
        CustomerResponseDto customerResponseDto = customerService.getCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDto);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable Long customerId, @RequestBody UpdateCustomerRequestDto updateCustomerRequestDto) {
        CustomerResponseDto updatedCustomerResponseDto = customerService.updateCustomerById(customerId, updateCustomerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerResponseDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<MessageResponseDto> deleteCustomerById(@PathVariable Long customerId) {
        customerService.deleteCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Customer with id " + customerId + " deleted successfully"));
    }

}
