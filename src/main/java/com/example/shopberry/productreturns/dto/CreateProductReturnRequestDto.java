package com.example.shopberry.productreturns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductReturnRequestDto {

    private Long orderId;

    private Long productId;

    private Long causeOfReturnId;

}
