package com.example.shopberry.orderproducts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderProductRequestDto {

    private Long orderId;

    private Long productId;

    private Long productQuantity;

    private Double productPrice;

}
