package com.example.shopberry.domain.orderstatuses.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"order_status_id", "order_status_name", "description"})
public class OrderStatusResponseDto {

    @JsonProperty("order_status_id")
    private Long orderStatusId;

    @JsonProperty("order_status_name")
    private String orderStatusName;

    private String description;

}
