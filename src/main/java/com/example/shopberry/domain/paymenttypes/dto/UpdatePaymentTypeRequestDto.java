package com.example.shopberry.domain.paymenttypes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentTypeRequestDto {

    @JsonProperty("payment_name")
    private String paymentName;

}
