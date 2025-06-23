package com.example.shopberry.domain.customeraddresses.dto.request;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerAddressRequestDto {

    @Schema(
            description = "First name of the person",
            example = "John"
    )
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Last name of the person",
            example = "Doe"
    )
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "City of the person or company",
            example = "Warsaw"
    )
    @NotBlank
    private String city;

    @Schema(
            description = "Postal code of the address",
            example = "00-001"
    )
    @NotBlank
    @JsonProperty("postal_code")
    private String postalCode;

    @Schema(
            description = "Street name of the address",
            example = "Main Street"
    )
    @NotBlank
    private String street;

    @Schema(
            description = "House number of the address",
            example = "12A"
    )
    @NotBlank
    @JsonProperty("house_number")
    private String houseNumber;

    @Schema(
            description = "Apartment number, if applicable",
            example = "5",
            minLength = 1,
            defaultValue = "null",
            nullable = true
    )
    @NotEmptyIfPresent
    private String apartment;

    @Schema(
            description = "Phone number of the person",
            example = "+48 123 456 789"
    )
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

}
