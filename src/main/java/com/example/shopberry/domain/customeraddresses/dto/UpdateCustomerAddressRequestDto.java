package com.example.shopberry.domain.customeraddresses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerAddressRequestDto {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String city;

    @JsonProperty("postal_code")
    private String postalCode;

    private String street;

    @JsonProperty("house_number")
    private String houseNumber;

    private String apartment;

    @JsonProperty("phone_number")
    private String phoneNumber;

}
