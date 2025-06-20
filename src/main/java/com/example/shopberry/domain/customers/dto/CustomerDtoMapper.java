package com.example.shopberry.domain.customers.dto;

import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressDtoMapper;
import com.example.shopberry.domain.customers.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerDtoMapper {

    private final CustomerAddressDtoMapper customerAddressDtoMapper;

    public CustomerResponseDto toDto(Customer customer) {
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();

        customerResponseDto.setCustomerId(customer.getUserId());
        customerResponseDto.setCreatedAt(customer.getCreatedAt());
        customerResponseDto.setIsCompany(customer.getIsCompany());
        customerResponseDto.setMainAddress(customerAddressDtoMapper.toDto(customer.getMainAddress()));

        return customerResponseDto;
    }

    public List<CustomerResponseDto> toDtoList(List<Customer> customerList) {
        return customerList.stream()
                .map(this::toDto)
                .toList();
    }

}
