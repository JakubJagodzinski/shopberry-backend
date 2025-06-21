package com.example.shopberry.domain.customers.dto;

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
public class UpdateCustomerRequestDto {

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
            description = "Indicates if customer is a company",
            nullable = true
    )
    @JsonProperty("is_company")
    private Boolean isCompany;

    @Schema(
            description = "Customer main address' id",
            example = "1",
            nullable = true
    )
    @JsonProperty("main_address_id")
    private Long mainAddressId;

}
