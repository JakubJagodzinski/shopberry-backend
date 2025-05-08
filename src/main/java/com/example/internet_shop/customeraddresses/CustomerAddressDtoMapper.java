package com.example.internet_shop.customeraddresses;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerAddressDtoMapper {

    public CustomerAddressDto toDto(CustomerAddress customerAddress) {
        CustomerAddressDto customerAddressDto = new CustomerAddressDto();

        customerAddressDto.setAddressId(customerAddress.getAddressId());
        customerAddressDto.setCustomerId(customerAddress.getCustomer().getCustomerId());
        customerAddressDto.setFirstName(customerAddress.getFirstName());
        customerAddressDto.setLastName(customerAddress.getLastName());
        customerAddressDto.setCity(customerAddress.getCity());
        customerAddressDto.setPostalCode(customerAddress.getPostalCode());
        customerAddressDto.setStreet(customerAddress.getStreet());
        customerAddressDto.setHouseNumber(customerAddress.getHouseNumber());
        customerAddressDto.setApartment(customerAddress.getApartment());
        customerAddressDto.setPhoneNumber(customerAddress.getPhoneNumber());

        return customerAddressDto;
    }

    public List<CustomerAddressDto> toDtoList(List<CustomerAddress> customerAddress) {
        return customerAddress.stream()
                .map(this::toDto)
                .toList();
    }

}
