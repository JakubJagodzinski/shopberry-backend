package com.example.shopberry.domain.paymenttypes.dto.request;

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
public class CreatePaymentTypeRequestDto {

    @Schema(
            description = "Unique name of the payment type",
            example = "PayPal"
    )
    @NotBlank
    @JsonProperty("payment_name")
    private String paymentName;

}
