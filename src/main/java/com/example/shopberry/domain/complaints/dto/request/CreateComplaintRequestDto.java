package com.example.shopberry.domain.complaints.dto.request;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintRequestDto {

    @Schema(
            description = "Id of the order containing the product subject to the complaint",
            example = "1"
    )
    @NotNull
    @JsonProperty("order_id")
    private Long orderId;

    @Schema(
            description = "Id of the product subject to the complaint",
            example = "1"
    )
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    @Schema(
            description = "Optional description about the complaint",
            example = "The product broke after only a week of using it",
            minLength = 1,
            maxLength = 500,
            defaultValue = "null",
            nullable = true
    )
    @NotEmptyIfPresent
    private String info;

    @Schema(
            description = "First name of the person submitting the complaint",
            example = "John"
    )
    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @Schema(
            description = "Last name of the person submitting the complaint",
            example = "Doe"
    )
    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Schema(
            description = "Company tax identification number (NIP), if applicable",
            example = "1234567890",
            minLength = 1,
            defaultValue = "null",
            nullable = true
    )
    @NotEmptyIfPresent
    private String nip;

    @Schema(
            description = "City of the person or company submitting the complaint",
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
            description = "Phone number of the person submitting the complaint",
            example = "+48 123 456 789"
    )
    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

}
