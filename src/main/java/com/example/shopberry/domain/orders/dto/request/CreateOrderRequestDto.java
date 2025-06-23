package com.example.shopberry.domain.orders.dto.request;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.example.shopberry.domain.orderproducts.dto.request.AddProductToOrderRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequestDto {

    @Schema(
            description = "Unique identifier of the customer placing the order",
            example = "e7b8f845-2f1b-4b8c-8f28-123456789abc"
    )
    @NotNull
    @JsonProperty("customer_id")
    private UUID customerId;

    @Schema(
            description = "ID of the selected shipment method (e.g., courier, parcel locker)",
            example = "2"
    )
    @NotNull
    @JsonProperty("shipment_type_id")
    private Long shipmentTypeId;

    @Schema(
            description = "Shipment tracking number or identifier, if available",
            example = "PK123456789PL",
            minLength = 1,
            defaultValue = "null",
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("shipment_identifier")
    private String shipmentIdentifier;

    @Schema(
            description = "ID of the selected payment method (e.g., bank transfer, online, cash on delivery)",
            example = "1"
    )
    @NotNull
    @JsonProperty("payment_type_id")
    private Long paymentTypeId;

    @Schema(
            description = "Whether an invoice is requested for this order",
            example = "true",
            defaultValue = "false",
            nullable = true
    )
    @JsonProperty("is_invoice")
    private Boolean isInvoice;

    @Schema(
            description = "List of products in order"
    )
    @NotNull
    private List<AddProductToOrderRequestDto> products;

}
