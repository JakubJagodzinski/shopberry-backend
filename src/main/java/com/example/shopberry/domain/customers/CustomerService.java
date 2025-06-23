package com.example.shopberry.domain.customers;

import com.example.shopberry.auth.access.manager.CustomerAccessManager;
import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.domain.customeraddresses.CustomerAddress;
import com.example.shopberry.domain.customeraddresses.CustomerAddressRepository;
import com.example.shopberry.domain.customers.dto.CustomerDtoMapper;
import com.example.shopberry.domain.customers.dto.response.CustomerResponseDto;
import com.example.shopberry.domain.customers.dto.request.UpdateCustomerRequestDto;
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
    private final CustomerAddressRepository customerAddressRepository;

    private final CustomerDtoMapper customerDtoMapper;

    private final CustomerAccessManager customerAccessManager;

    public List<CustomerResponseDto> getAllCustomers() {
        return customerDtoMapper.toDtoList(customerRepository.findAll());
    }

    @Transactional
    public CustomerResponseDto getCustomerById(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        return customerDtoMapper.toDto(customer);
    }

    @Transactional
    public CustomerResponseDto updateCustomerById(UUID customerId, UpdateCustomerRequestDto updateCustomerRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        customerAccessManager.checkCanUpdateCustomer(customer);

        if (updateCustomerRequestDto.getFirstName() != null) {
            customer.setFirstName(updateCustomerRequestDto.getFirstName());
        }

        if (updateCustomerRequestDto.getLastName() != null) {
            customer.setLastName(updateCustomerRequestDto.getLastName());
        }

        if (updateCustomerRequestDto.getIsCompany() != null) {
            customer.setIsCompany(updateCustomerRequestDto.getIsCompany());
        }

        if (updateCustomerRequestDto.getMainAddressId() != null) {
            CustomerAddress customerAddress = customerAddressRepository.findById(updateCustomerRequestDto.getMainAddressId()).orElse(null);

            if (customerAddress == null) {
                throw new EntityNotFoundException(CustomerMessages.CUSTOMER_ADDRESS_NOT_FOUND);
            }

            customer.setMainAddress(customerAddress);
        }

        return customerDtoMapper.toDto(customerRepository.save(customer));
    }

    @Transactional
    public void deleteCustomerById(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        customerAccessManager.checkCanDeleteCustomer(customer);

        customerRepository.deleteById(customerId);
    }

}
