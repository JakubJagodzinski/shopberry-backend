package com.example.shopberry.domain.orders.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOrderRequestDto {

    @Schema(
            description = "Describes order current status",
            example = "1",
            nullable = true
    )
    @JsonProperty("order_status_id")
    private Long orderStatusId;

}
