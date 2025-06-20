package com.example.shopberry.domain.orders.dto;

import com.example.shopberry.domain.orderstatuses.dto.OrderStatusResponseDto;
import com.example.shopberry.domain.paymenttypes.dto.PaymentTypeResponseDto;
import com.example.shopberry.domain.shipmenttypes.dto.ShipmentTypeResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderResponseDto {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("sent_at")
    private LocalDateTime sentAt;

    @JsonProperty("order_status")
    private OrderStatusResponseDto orderStatus;

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("shipment_type")
    private ShipmentTypeResponseDto shipmentType;

    @JsonProperty("shipment_identifier")
    private String shipmentIdentifier;

    @JsonProperty("payment_type")
    private PaymentTypeResponseDto paymentType;

    @JsonProperty("is_payment_recorded")
    private Boolean isPaymentRecorded;

    @JsonProperty("is_invoice")
    private Boolean isInvoice;

}
