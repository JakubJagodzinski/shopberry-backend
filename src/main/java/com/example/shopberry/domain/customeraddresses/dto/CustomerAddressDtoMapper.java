package com.example.shopberry.domain.customeraddresses.dto;

import com.example.shopberry.domain.customeraddresses.CustomerAddress;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerAddressDtoMapper {

    public CustomerAddressResponseDto toDto(CustomerAddress customerAddress) {
        if (customerAddress == null) {
            return null;
        }

        CustomerAddressResponseDto customerAddressResponseDto = new CustomerAddressResponseDto();

        customerAddressResponseDto.setAddressId(customerAddress.getAddressId());
        customerAddressResponseDto.setCustomerId(customerAddress.getCustomer().getUserId());
        customerAddressResponseDto.setFirstName(customerAddress.getFirstName());
        customerAddressResponseDto.setLastName(customerAddress.getLastName());
        customerAddressResponseDto.setCity(customerAddress.getCity());
        customerAddressResponseDto.setPostalCode(customerAddress.getPostalCode());
        customerAddressResponseDto.setStreet(customerAddress.getStreet());
        customerAddressResponseDto.setHouseNumber(customerAddress.getHouseNumber());
        customerAddressResponseDto.setApartment(customerAddress.getApartment());
        customerAddressResponseDto.setPhoneNumber(customerAddress.getPhoneNumber());

        return customerAddressResponseDto;
    }

    public List<CustomerAddressResponseDto> toDtoList(List<CustomerAddress> customerAddressList) {
        return customerAddressList.stream()
                .map(this::toDto)
                .toList();
    }

}
