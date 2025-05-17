package com.example.shopberry.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {

    private Long customerId;

    private Long shipmentTypeId;

    private String shipmentIdentifier;

    private Long paymentTypeId;

    private Boolean isPaymentRecorded;

    private Boolean isInvoice;

}
