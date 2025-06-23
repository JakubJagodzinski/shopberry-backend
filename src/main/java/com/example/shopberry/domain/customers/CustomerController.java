package com.example.shopberry.domain.customers;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.domain.customers.dto.request.UpdateCustomerRequestDto;
import com.example.shopberry.domain.customers.dto.response.CustomerResponseDto;
import com.example.shopberry.user.Permission;
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

    @CheckPermission(Permission.CUSTOMER_READ_ALL)
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customerResponseDtoList = customerService.getAllCustomers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDtoList);
    }

    @CheckPermission(Permission.CUSTOMER_READ)
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable UUID customerId) {
        CustomerResponseDto customerResponseDto = customerService.getCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_UPDATE)
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomerById(@PathVariable UUID customerId, @Valid @RequestBody UpdateCustomerRequestDto updateCustomerRequestDto) {
        CustomerResponseDto updatedCustomerResponseDto = customerService.updateCustomerById(customerId, updateCustomerRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCustomerResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_DELETE)
    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable UUID customerId) {
        customerService.deleteCustomerById(customerId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
