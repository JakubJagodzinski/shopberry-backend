package com.example.internet_shop.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {

    private Long orderId;

    private Long customerId;

    private Long shipmentTypeId;

    private String shipmentIdentifier;

    private Long paymentTypeId;

    private Boolean isPaymentRecorded;

    private Boolean isInvoice;

}
