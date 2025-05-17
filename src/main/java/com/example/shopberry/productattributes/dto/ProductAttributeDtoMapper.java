package com.example.shopberry.productattributes.dto;

import com.example.shopberry.productattributes.ProductAttribute;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductAttributeDtoMapper {

    public ProductAttributeResponseDto toDto(ProductAttribute productAttribute) {
        ProductAttributeResponseDto dto = new ProductAttributeResponseDto();

        dto.setProductId(productAttribute.getProduct().getProductId());
        dto.setAttributeId(productAttribute.getAttribute().getAttributeId());
        dto.setValue(productAttribute.getValue());

        return dto;
    }

    public List<ProductAttributeResponseDto> toDtoList(List<ProductAttribute> productAttributes) {
        return productAttributes.stream()
                .map(this::toDto)
                .toList();
    }

}
