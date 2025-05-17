package com.example.shopberry.domain.customeraddresses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressResponseDto {

    private Long addressId;

    private Long customerId;

    private String firstName;

    private String lastName;

    private String city;

    private String postalCode;

    private String street;

    private String houseNumber;

    private String apartment;

    private String phoneNumber;

}
