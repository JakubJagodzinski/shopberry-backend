package com.example.shopberry.domain.customers;

import com.example.shopberry.domain.customers.dto.CustomerDtoMapper;
import com.example.shopberry.domain.customers.dto.CustomerResponseDto;
import com.example.shopberry.domain.customers.dto.UpdateCustomerRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public CustomerResponseDto getCustomerById(Long id) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return customerDtoMapper.toDto(customer);
    }

    @Transactional
    public CustomerResponseDto updateCustomerById(Long id, UpdateCustomerRequestDto updateCustomerRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (updateCustomerRequestDto.getIsCompany() != null) {
            customer.setIsCompany(updateCustomerRequestDto.getIsCompany());
        }

        return customerDtoMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public void deleteCustomerById(Long id) throws EntityNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        customerRepository.deleteById(id);
    }

}
