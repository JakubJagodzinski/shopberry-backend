package com.example.shopberry.domain.productattributes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeId {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "attribute_id")
    private Long attributeId;

}
