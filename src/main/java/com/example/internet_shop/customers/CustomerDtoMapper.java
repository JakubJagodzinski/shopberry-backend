package com.example.internet_shop.customers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDtoMapper {

    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();

        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setIsCompany(customer.getIsCompany());

        return customerDto;
    }

    public List<CustomerDto> toDtoList(List<Customer> customers) {
        return customers.stream()
                .map(this::toDto)
                .toList();
    }

}
