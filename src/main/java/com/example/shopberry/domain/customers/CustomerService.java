package com.example.shopberry.domain.customers;

import com.example.shopberry.domain.customers.dto.CustomerDtoMapper;
import com.example.shopberry.domain.customers.dto.CustomerResponseDto;
import com.example.shopberry.domain.customers.dto.UpdateCustomerRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";

    public List<CustomerResponseDto> getAllCustomers() {
        return customerDtoMapper.toDtoList(customerRepository.findAll());
    }

    @Transactional
    public CustomerResponseDto getCustomerById(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return customerDtoMapper.toDto(customer);
    }

    @Transactional
    public CustomerResponseDto updateCustomerById(UUID customerId, UpdateCustomerRequestDto updateCustomerRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (updateCustomerRequestDto.getIsCompany() != null) {
            customer.setIsCompany(updateCustomerRequestDto.getIsCompany());
        }

        return customerDtoMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public void deleteCustomerById(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        customerRepository.deleteById(customerId);
    }

}
