package com.example.shopberry.productreturns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReturnResponseDto {

    private Long productReturnId;

    private Long orderId;

    private Long productId;

    private Long causeOfReturnId;

}
