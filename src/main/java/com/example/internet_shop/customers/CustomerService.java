package com.example.internet_shop.customers;

import com.example.internet_shop.customers.dto.CreateCustomerRequestDto;
import com.example.internet_shop.customers.dto.CustomerDtoMapper;
import com.example.internet_shop.customers.dto.CustomerResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String CUSTOMER_WITH_THAT_EMAIL_ALREADY_EXISTS_MESSAGE = "Customer with that email already exists";

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
    }

    public List<CustomerResponseDto> getCustomers() {
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
    public CustomerResponseDto createCustomer(CreateCustomerRequestDto createCustomerRequestDto) throws IllegalArgumentException {
        if (customerRepository.existsByEmail(createCustomerRequestDto.getEmail())) {
            throw new IllegalArgumentException(CUSTOMER_WITH_THAT_EMAIL_ALREADY_EXISTS_MESSAGE);
        }

        Customer customer = new Customer();

        customer.setEmail(createCustomerRequestDto.getEmail());
        customer.setPassword(createCustomerRequestDto.getPassword());
        customer.setIsCompany(createCustomerRequestDto.getIsCompany());

        return customerDtoMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerResponseDto updateCustomerById(Long id, CreateCustomerRequestDto createCustomerRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (createCustomerRequestDto.getEmail() != null) {
            Customer otherCustomer = customerRepository.findByEmail(createCustomerRequestDto.getEmail());

            if (otherCustomer != null && !customer.getCustomerId().equals(otherCustomer.getCustomerId())) {
                throw new IllegalArgumentException(CUSTOMER_WITH_THAT_EMAIL_ALREADY_EXISTS_MESSAGE);
            }

            customer.setEmail(createCustomerRequestDto.getEmail());
        }

        if (createCustomerRequestDto.getPassword() != null) {
            customer.setPassword(createCustomerRequestDto.getPassword());
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
