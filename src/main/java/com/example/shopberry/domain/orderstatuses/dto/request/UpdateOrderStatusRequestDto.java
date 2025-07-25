package com.example.shopberry.domain.orderstatuses.dto.request;

import com.example.shopberry.common.validation.NotEmptyIfPresent;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequestDto {

    @Schema(
            description = "Unique name of the order status",
            example = "Confirmed",
            minLength = 1,
            nullable = true
    )
    @NotEmptyIfPresent
    @JsonProperty("order_status_name")
    private String orderStatusName;

    @Schema(
            description = "Detailed description of order status",
            example = "The order is temporarily on hold",
            minLength = 1,
            maxLength = 500,
            nullable = true
    )
    @NotEmptyIfPresent
    private String description;

}
