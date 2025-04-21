package com.example.internet_shop.customers;

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

    public List<CustomerDto> getCustomers() {
        return customerDtoMapper.toDtoList(customerRepository.findAll());
    }

    @Transactional
    public CustomerDto getCustomerById(Long id) throws EntityNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return customerDtoMapper.toDto(customerRepository.getReferenceById(id));
    }

    @Transactional
    public CustomerDto createCustomer(CreateCustomerDto createCustomerDto) throws IllegalStateException {
        if (customerRepository.existsByEmail(createCustomerDto.getEmail())) {
            throw new IllegalStateException(CUSTOMER_WITH_THAT_EMAIL_ALREADY_EXISTS_MESSAGE);
        }

        Customer customer = new Customer();

        customer.setEmail(createCustomerDto.getEmail());
        customer.setPassword(createCustomerDto.getPassword());
        customer.setIsCompany(createCustomerDto.getIsCompany());

        return customerDtoMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDto editCustomerById(Long id, CreateCustomerDto createCustomerDto) throws EntityNotFoundException, IllegalStateException {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Customer customer = customerRepository.getReferenceById(id);

        if (createCustomerDto.getEmail() != null) {
            Customer otherCustomer = customerRepository.findByEmail(createCustomerDto.getEmail());

            if (otherCustomer != null && !customer.getCustomerId().equals(otherCustomer.getCustomerId())) {
                throw new IllegalStateException(CUSTOMER_WITH_THAT_EMAIL_ALREADY_EXISTS_MESSAGE);
            }

            customer.setEmail(createCustomerDto.getEmail());
        }

        if (createCustomerDto.getPassword() != null) {
            customer.setPassword(createCustomerDto.getPassword());
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
