package com.example.internet_shop.productattributes;

import com.example.internet_shop.attributes.Attribute;
import com.example.internet_shop.attributes.ProductAttributeId;
import com.example.internet_shop.products.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "product_attributes")
public class ProductAttribute {

    @EmbeddedId
    private ProductAttributeId productAttributeId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id")
    private Attribute attribute;

    @Column(nullable = false)
    private Double value;

}
