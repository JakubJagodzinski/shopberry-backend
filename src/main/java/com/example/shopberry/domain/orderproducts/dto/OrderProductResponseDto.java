package com.example.shopberry.domain.orderproducts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDto {

    private Long orderId;

    private Long productId;

    private Long productQuantity;

    private Double productPrice;

    private Long orderProductStatusId;

}
