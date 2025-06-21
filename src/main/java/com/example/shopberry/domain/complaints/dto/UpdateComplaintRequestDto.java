package com.example.shopberry.domain.complaints.dto;

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
public class UpdateComplaintRequestDto {

    @Schema(
            description = "Optional description about the complaint",
            example = "The product broke after only a week of using it",
            minLength = 1,
            maxLength = 500,
            nullable = true
    )
    @NotEmptyIfPresent
    private String info;

    @Schema(
            description = "First name of the person submitting the complaint",
            example = "John",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Last name of the person submitting the complaint",
            example = "Doe",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "Company tax identification number (NIP), if applicable",
            example = "1234567890",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    private String nip;

    @Schema(
            description = "City of the person or company submitting the complaint",
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
            description = "Phone number of the person submitting the complaint",
            example = "+48 123 456 789",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("phone_number")
    private String phoneNumber;

}
