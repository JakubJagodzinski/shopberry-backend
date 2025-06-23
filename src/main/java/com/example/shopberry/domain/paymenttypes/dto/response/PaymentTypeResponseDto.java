package com.example.shopberry.domain.paymenttypes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"payment_type_id", "payment_name"})
public class PaymentTypeResponseDto {

    @JsonProperty("payment_type_id")
    private Long paymentTypeId;

    @JsonProperty("payment_name")
    private String paymentName;

}
