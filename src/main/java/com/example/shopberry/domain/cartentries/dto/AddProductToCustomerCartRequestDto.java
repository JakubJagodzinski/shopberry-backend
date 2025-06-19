package com.example.shopberry.domain.cartentries.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToCustomerCartRequestDto {

    @JsonProperty("product_id")
    private Long productId;

    private Long quantity;

}
