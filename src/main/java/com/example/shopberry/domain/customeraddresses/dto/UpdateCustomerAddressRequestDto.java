package com.example.shopberry.domain.customeraddresses.dto;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerAddressRequestDto {

    @Schema(
            description = "First name of the person",
            example = "John",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Last name of the person",
            example = "Doe",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "City of the person or company",
            example = "Warsaw",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    private String city;

    @Schema(
            description = "Postal code of the address",
            example = "00-001",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("postal_code")
    private String postalCode;

    @Schema(
            description = "Street name of the address",
            example = "Main Street",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    private String street;

    @Schema(
            description = "House number of the address",
            example = "12A",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("house_number")
    private String houseNumber;

    @Schema(
            description = "Apartment number, if applicable",
            example = "5",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    private String apartment;

    @Schema(
            description = "Phone number of the person",
            example = "+48 123 456 789",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("phone_number")
    private String phoneNumber;

}
