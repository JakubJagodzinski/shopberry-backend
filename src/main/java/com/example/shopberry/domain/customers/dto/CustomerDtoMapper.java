package com.example.shopberry.domain.customers.dto;

import com.example.shopberry.domain.customers.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDtoMapper {

    public CustomerResponseDto toDto(Customer customer) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        customerResponseDto.setCustomerId(customer.getCustomerId());
        customerResponseDto.setEmail(customer.getEmail());
        customerResponseDto.setCreatedAt(customer.getCreatedAt());
        customerResponseDto.setIsCompany(customer.getIsCompany());

        return customerResponseDto;
    }

    public List<CustomerResponseDto> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(this::toDto)
                .toList();
    }

}
