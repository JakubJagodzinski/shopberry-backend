package com.example.shopberry.domain.orders.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("shipment_type_id")
    private Long shipmentTypeId;

    @JsonProperty("shipment_identifier")
    private String shipmentIdentifier;

    @JsonProperty("payment_type_id")
    private Long paymentTypeId;

    @JsonProperty("is_payment_recorded")
    private Boolean isPaymentRecorded;

    @JsonProperty("is_invoice")
    private Boolean isInvoice;

}
