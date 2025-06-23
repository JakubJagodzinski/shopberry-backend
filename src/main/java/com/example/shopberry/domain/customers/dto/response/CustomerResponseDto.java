package com.example.shopberry.domain.customers.dto.response;

import com.example.shopberry.domain.customeraddresses.dto.response.CustomerAddressResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customer_id", "first_name", "last_name", "created_at", "is_company", "main_address"})
public class CustomerResponseDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("is_company")
    private Boolean isCompany;

    @JsonProperty("main_address")
    private CustomerAddressResponseDto mainAddress;

}
