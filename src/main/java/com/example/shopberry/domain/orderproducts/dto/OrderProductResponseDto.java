package com.example.shopberry.domain.orderproducts.dto;

import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusResponseDto;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDto {

    @JsonProperty("order_id")
    private Long orderId;

    private ProductResponseDto product;

    @JsonProperty("product_quantity")
    private Long productQuantity;

    @JsonProperty("product_price")
    private Double productPrice;

    @JsonProperty("order_product_status")
    private OrderProductStatusResponseDto orderProductStatus;

}
