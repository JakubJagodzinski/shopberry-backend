package com.example.internet_shop.productattributes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, ProductAttributeId> {

    List<ProductAttribute> findById_ProductId(Long productId);

    List<ProductAttribute> findById_AttributeId(Long attributeId);

}
