package com.example.internet_shop.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private Long orderId;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    private Long orderStatusId;

    private Long customerId;

    private Long shipmentTypeId;

    private String shipmentIdentifier;

    private Long paymentTypeId;

    private Boolean isPaymentRecorded;

    private Boolean isInvoice;

}
