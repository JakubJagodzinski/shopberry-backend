package com.example.shopberry.domain.productattributes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductAttributeId {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "attribute_id")
    private Long attributeId;

}
