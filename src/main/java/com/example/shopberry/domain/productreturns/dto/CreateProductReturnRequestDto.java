package com.example.shopberry.domain.productreturns.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductReturnRequestDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("cause_of_return_id")
    private Long causeOfReturnId;

}
