package com.example.shopberry.paymenttypes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeResponseDto {

    private Long paymentTypeId;

    private String paymentName;

}
