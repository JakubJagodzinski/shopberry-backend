package com.example.shopberry.domain.customeraddresses.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"address_id", "customer_id", "first_name", "last_name", "city", "postal_code", "street", "house_number", "apartment", "phone_number"})
public class CustomerAddressResponseDto {

    @JsonProperty("address_id")
    private Long addressId;

    @JsonProperty("customer_id")
    private UUID customerId;

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
