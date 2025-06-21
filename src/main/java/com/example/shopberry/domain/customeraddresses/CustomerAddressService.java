package com.example.shopberry.domain.customeraddresses;

import com.example.shopberry.common.constants.messages.CustomerMessages;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerAddressService {

    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerRepository customerRepository;

    private final CustomerAddressDtoMapper customerAddressDtoMapper;

    public List<CustomerAddressResponseDto> getAllAddresses() {
        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAll());
    }

    @Transactional
    public List<CustomerAddressResponseDto> getCustomerAllAddresses(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        return customerAddressDtoMapper.toDtoList(customerAddressRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public CustomerAddressResponseDto createAddress(UUID customerId, CreateCustomerAddressRequestDto createCustomerAddressRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        CustomerAddress customerAddress = new CustomerAddress();

        customerAddress.setCustomer(customer);
        customerAddress.setFirstName(createCustomerAddressRequestDto.getFirstName().trim());
        customerAddress.setLastName(createCustomerAddressRequestDto.getLastName().trim());
        customerAddress.setCity(createCustomerAddressRequestDto.getCity().trim());
        customerAddress.setPostalCode(createCustomerAddressRequestDto.getPostalCode().trim());
        customerAddress.setStreet(createCustomerAddressRequestDto.getStreet().trim());
        customerAddress.setHouseNumber(createCustomerAddressRequestDto.getHouseNumber().trim());
        customerAddress.setApartment(createCustomerAddressRequestDto.getApartment().trim());
        customerAddress.setPhoneNumber(createCustomerAddressRequestDto.getPhoneNumber().trim());

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public CustomerAddressResponseDto updateAddressById(Long customerAddressId, UpdateCustomerAddressRequestDto updateCustomerAddressRequestDto) throws EntityNotFoundException {
        CustomerAddress customerAddress = customerAddressRepository.findById(customerAddressId).orElse(null);

        if (customerAddress == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_ADDRESS_NOT_FOUND);
        }

        if (updateCustomerAddressRequestDto.getFirstName() != null) {
            customerAddress.setFirstName(updateCustomerAddressRequestDto.getFirstName().trim());
        }

        if (updateCustomerAddressRequestDto.getLastName() != null) {
            customerAddress.setLastName(updateCustomerAddressRequestDto.getLastName().trim());
        }

        if (updateCustomerAddressRequestDto.getCity() != null) {
            customerAddress.setCity(updateCustomerAddressRequestDto.getCity().trim());
        }

        if (updateCustomerAddressRequestDto.getPostalCode() != null) {
            customerAddress.setPostalCode(updateCustomerAddressRequestDto.getPostalCode().trim());
        }

        if (updateCustomerAddressRequestDto.getStreet() != null) {
            customerAddress.setStreet(updateCustomerAddressRequestDto.getStreet().trim());
        }

        if (updateCustomerAddressRequestDto.getHouseNumber() != null) {
            customerAddress.setHouseNumber(updateCustomerAddressRequestDto.getHouseNumber().trim());
        }

        if (updateCustomerAddressRequestDto.getApartment() != null) {
            customerAddress.setApartment(updateCustomerAddressRequestDto.getApartment().trim());
        }

        if (updateCustomerAddressRequestDto.getPhoneNumber() != null) {
            customerAddress.setPhoneNumber(updateCustomerAddressRequestDto.getPhoneNumber().trim());
        }

        return customerAddressDtoMapper.toDto(customerAddressRepository.save(customerAddress));
    }

    @Transactional
    public void deleteCustomerAllAddresses(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        customerAddressRepository.deleteAllByCustomer_UserId(customerId);
    }

    @Transactional
    public void deleteAddressById(Long customerAddressId) throws EntityNotFoundException {
        if (!customerAddressRepository.existsById(customerAddressId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_ADDRESS_NOT_FOUND);
        }

        customerAddressRepository.deleteById(customerAddressId);
    }

}
