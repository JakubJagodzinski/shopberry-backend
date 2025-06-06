package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.domain.customeraddresses.dto.CreateCustomerAddressRequestDto;
import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressDtoMapper;
import com.example.shopberry.domain.customeraddresses.dto.CustomerAddressResponseDto;
import com.example.shopberry.domain.customeraddresses.dto.UpdateCustomerAddressRequestDto;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;

    private final CustomerAddressDtoMapper customerAddressDtoMapper;

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private static final String CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE = "Customer address not found";
    private static final String FIRST_NAME_CANNOT_BE_EMPTY_MESSAGE = "First name cannot be empty";
    private static final String LAST_NAME_CANNOT_BE_EMPTY_MESSAGE = "Last name cannot be empty";

    public List<CustomerAddressResponseDto> getCustomerAddresses() {
        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAll());
    }

    @Transactional
    public List<CustomerAddressResponseDto> getCustomerAddressesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAllByCustomer_Id(customerId));
    }

    @Transactional
    public CustomerAddressResponseDto createCustomerAddress(CreateCustomerAddressRequestDto createCustomerAddressRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(createCustomerAddressRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        CustomerAddress customerAddress = new CustomerAddress();

        customerAddress.setCustomer(customer);
        customerAddress.setFirstName(createCustomerAddressRequestDto.getFirstName());
        customerAddress.setLastName(createCustomerAddressRequestDto.getLastName());
        customerAddress.setCity(createCustomerAddressRequestDto.getCity());
        customerAddress.setPostalCode(createCustomerAddressRequestDto.getPostalCode());
        customerAddress.setStreet(createCustomerAddressRequestDto.getStreet());
        customerAddress.setHouseNumber(createCustomerAddressRequestDto.getHouseNumber());
        customerAddress.setApartment(createCustomerAddressRequestDto.getApartment());
        customerAddress.setPhoneNumber(createCustomerAddressRequestDto.getPhoneNumber());

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public CustomerAddressResponseDto updateCustomerAddressById(Long customerAddressId, UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        CustomerAddress customerAddress = customerAddressRepository.findById(customerAddressId).orElse(null);

        if (customerAddress == null) {
            throw new EntityNotFoundException(CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE);
        }

        if (updateCustomerAddressRequestDto.getFirstName() != null) {
            if (updateCustomerAddressRequestDto.getFirstName().isEmpty()) {
                throw new IllegalArgumentException(FIRST_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            customerAddress.setFirstName(updateCustomerAddressRequestDto.getFirstName());
        }

        if (updateCustomerAddressRequestDto.getLastName() != null) {
            if (updateCustomerAddressRequestDto.getLastName().isEmpty()) {
                throw new IllegalArgumentException(LAST_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            customerAddress.setLastName(updateCustomerAddressRequestDto.getLastName());
        }

        if (updateCustomerAddressRequestDto.getCity() != null) {
            customerAddress.setCity(updateCustomerAddressRequestDto.getCity());
        }

        if (updateCustomerAddressRequestDto.getPostalCode() != null) {
            customerAddress.setPostalCode(updateCustomerAddressRequestDto.getPostalCode());
        }

        if (updateCustomerAddressRequestDto.getStreet() != null) {
            customerAddress.setStreet(updateCustomerAddressRequestDto.getStreet());
        }

        if (updateCustomerAddressRequestDto.getHouseNumber() != null) {
            customerAddress.setHouseNumber(updateCustomerAddressRequestDto.getHouseNumber());
        }

        if (updateCustomerAddressRequestDto.getApartment() != null) {
            customerAddress.setApartment(updateCustomerAddressRequestDto.getApartment());
        }

        if (updateCustomerAddressRequestDto.getPhoneNumber() != null) {
            customerAddress.setPhoneNumber(updateCustomerAddressRequestDto.getPhoneNumber());
        }

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public void deleteCustomerAddressesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        customerAddressRepository.deleteAllByCustomer_Id(customerId);
    }

    @Transactional
    public void deleteCustomerAddressById(Long customerAddressId) throws EntityNotFoundException {
        if (!customerAddressRepository.existsById(customerAddressId)) {
            throw new EntityNotFoundException(CUSTOMER_ADDRESS_NOT_FOUND_MESSAGE);
        }

        customerAddressRepository.deleteById(customerAddressId);
    }

}
