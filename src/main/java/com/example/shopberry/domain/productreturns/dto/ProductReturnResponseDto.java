package com.example.shopberry.domain.productreturns.dto;

import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnResponseDto;
import com.example.shopberry.domain.orders.dto.OrderResponseDto;
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
public class ProductReturnResponseDto {

    @JsonProperty("product_return_id")
    private Long productReturnId;

    private OrderResponseDto order;

    private ProductResponseDto product;

    @JsonProperty("cause_of_return")
    private CauseOfReturnResponseDto causeOfReturn;

}
