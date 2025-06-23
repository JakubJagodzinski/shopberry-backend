package com.example.shopberry.domain.orderproductstatuses.dto.response;

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
@JsonPropertyOrder({"order_product_status_id", "status_name", "description"})
public class OrderProductStatusResponseDto {

    @JsonProperty("order_product_status_id")
    private Long orderProductStatusId;

    @JsonProperty("status_name")
    private String statusName;

    private String description;

}
