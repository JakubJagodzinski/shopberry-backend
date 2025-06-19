package com.example.shopberry.domain.productattributes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, ProductAttributeId> {

    List<ProductAttribute> findAllByProduct_ProductId(Long productId);

    List<ProductAttribute> findAllByAttribute_AttributeId(Long attributeId);

}
