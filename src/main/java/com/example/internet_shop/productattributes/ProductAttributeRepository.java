package com.example.internet_shop.productattributes;

import com.example.internet_shop.attributes.ProductAttributeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, ProductAttributeId> {
}
