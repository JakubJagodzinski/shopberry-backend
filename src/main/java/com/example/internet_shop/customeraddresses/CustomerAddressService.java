package com.example.internet_shop.customeraddresses;

import com.example.internet_shop.customers.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;

    private final CustomerAddressDtoMapper customerAddressDtoMapper;

    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE = "Customer address not found";
    private final String FIRST_NAME_CANNOT_BE_EMPTY_MESSAGE = "First name cannot be empty";
    private final String LAST_NAME_CANNOT_BE_EMPTY_MESSAGE = "Last name cannot be empty";

    public CustomerAddressService(CustomerAddressRepository customerAddressRepository, CustomerRepository customerRepository, CustomerAddressDtoMapper customerAddressDtoMapper) {
        this.customerAddressRepository = customerAddressRepository;
        this.customerRepository = customerRepository;
        this.customerAddressDtoMapper = customerAddressDtoMapper;
    }

    public List<CustomerAddressDto> getCustomerAddresses() {
        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAll());
    }

    @Transactional
    public List<CustomerAddressDto> getCustomerAddressesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAllByCustomer_CustomerId(customerId));
    }

    @Transactional
    public CustomerAddressDto createCustomerAddress(Long customerId, CreateCustomerAddressDto createCustomerAddressDto) throws EntityNotFoundException{
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        CustomerAddress customerAddress = new CustomerAddress();

        customerAddress.setCustomer(customerRepository.getReferenceById(customerId));
        customerAddress.setFirstName(createCustomerAddressDto.getFirstName());
        customerAddress.setLastName(createCustomerAddressDto.getLastName());
        customerAddress.setCity(createCustomerAddressDto.getCity());
        customerAddress.setPostalCode(createCustomerAddressDto.getPostalCode());
        customerAddress.setStreet(createCustomerAddressDto.getStreet());
        customerAddress.setHouseNumber(createCustomerAddressDto.getHouseNumber());
        customerAddress.setApartment(createCustomerAddressDto.getApartment());
        customerAddress.setPhoneNumber(createCustomerAddressDto.getPhoneNumber());

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public CustomerAddressDto updateCustomerAddressById(Long customerAddressId, UpdateCustomerAddressDto updateCustomerAddressDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!customerAddressRepository.existsById(customerAddressId)) {
            throw new EntityNotFoundException(CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE);
        }

        CustomerAddress customerAddress = customerAddressRepository.getReferenceById(customerAddressId);

        if (customerAddress.getFirstName() != null) {
            if (customerAddress.getFirstName().isEmpty()) {
                throw new IllegalArgumentException(FIRST_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            customerAddress.setFirstName(updateCustomerAddressDto.getFirstName());
        }

        if (customerAddress.getLastName() != null) {
            if (customerAddress.getLastName().isEmpty()) {
                throw new IllegalArgumentException(LAST_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            customerAddress.setLastName(updateCustomerAddressDto.getLastName());
        }

        if (updateCustomerAddressDto.getCity() != null) {
            customerAddress.setCity(updateCustomerAddressDto.getCity());
        }

        if (updateCustomerAddressDto.getPostalCode() != null) {
            customerAddress.setPostalCode(updateCustomerAddressDto.getPostalCode());
        }

        if (updateCustomerAddressDto.getStreet() != null) {
            customerAddress.setStreet(updateCustomerAddressDto.getStreet());
        }

        if (updateCustomerAddressDto.getHouseNumber() != null) {
            customerAddress.setHouseNumber(updateCustomerAddressDto.getHouseNumber());
        }

        if (updateCustomerAddressDto.getApartment() != null) {
            customerAddress.setApartment(updateCustomerAddressDto.getApartment());
        }

        if (updateCustomerAddressDto.getPhoneNumber() != null) {
            customerAddress.setPhoneNumber(updateCustomerAddressDto.getPhoneNumber());
        }

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public void deleteCustomerAddressesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        customerAddressRepository.deleteAllByCustomer_CustomerId(customerId);
    }

    @Transactional
    public void deleteCustomerAddressById(Long customerAddressId) throws EntityNotFoundException {
        if (!customerAddressRepository.existsById(customerAddressId)) {
            throw new EntityNotFoundException(CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE);
        }

        customerAddressRepository.deleteById(customerAddressId);
    }

}
