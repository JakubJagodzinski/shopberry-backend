package com.example.shopberry.domain.customers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequestDto {

    @JsonProperty("is_company")
    private Boolean isCompany;

    @JsonProperty("main_address_id")
    private Long mainAddressId;

}
