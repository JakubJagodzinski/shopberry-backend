package com.example.shopberry.domain.paymenttypes.dto;

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
public class UpdatePaymentTypeRequestDto {

    @Schema(
            description = "Unique name of the payment type",
            example = "PayPal",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("payment_name")
    private String paymentName;

}
