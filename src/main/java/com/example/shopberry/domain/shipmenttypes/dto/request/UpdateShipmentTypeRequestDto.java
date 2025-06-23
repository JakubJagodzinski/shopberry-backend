package com.example.shopberry.domain.shipmenttypes.dto.request;

import com.example.shopberry.common.constants.messages.ShipmentTypeMessages;
import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentTypeRequestDto {

    @Schema(
            description = "Unique name of the shipment type",
            example = "Air mail",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("shipment_name")
    private String shipmentName;

    @Schema(
            description = "Shipment cost",
            example = "15.99",
            minimum = "0.0",
            nullable = true
    )
    @Min(value = 0, message = ShipmentTypeMessages.SHIPMENT_COST_CANNOT_BE_NEGATIVE)
    @JsonProperty("shipment_cost")
    private Double shipmentCost;

}
