package com.example.shopberry.domain.productattributes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductAttributeRequestDto {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("attribute_id")
    private Long attributeId;

    private Double value;

}
