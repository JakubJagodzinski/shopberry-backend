package com.example.shopberry.productattributes;

import com.example.shopberry.attributes.Attribute;
import com.example.shopberry.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "product_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribute {

    @EmbeddedId
    private ProductAttributeId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_product_attribute_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", foreignKey = @ForeignKey(name = "fk_product_attribute_attribute"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attribute attribute;

    @Column(nullable = false)
    private Double value;

}
