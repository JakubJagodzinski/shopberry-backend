package com.example.shopberry.domain.orderstatuses.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderStatusRequestDto {

    @Schema(
            description = "Unique name of the order status",
            example = "Confirmed"
    )
    @NotBlank
    @JsonProperty("order_status_name")
    private String orderStatusName;

    @Schema(
            description = "Detailed description of order status",
            example = "The order is temporarily on hold",
            minLength = 1,
            maxLength = 500
    )
    @NotBlank
    private String description;

}
