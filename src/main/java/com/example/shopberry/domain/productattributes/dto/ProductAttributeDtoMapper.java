package com.example.shopberry.domain.productattributes.dto;

import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.productattributes.ProductAttribute;
import com.example.shopberry.domain.productattributes.dto.response.ProductAttributeResponseDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAttributeDtoMapper {

    private final ProductDtoMapper productDtoMapper;
    private final AttributeDtoMapper attributeDtoMapper;

    public ProductAttributeResponseDto toDto(ProductAttribute productAttribute) {
        if (productAttribute == null) {
            return null;
        }

        ProductAttributeResponseDto dto = new ProductAttributeResponseDto();

        dto.setProduct(productDtoMapper.toDto(productAttribute.getProduct()));
        dto.setAttribute(attributeDtoMapper.toDto(productAttribute.getAttribute()));
        dto.setValue(productAttribute.getValue());
        dto.setWeight(productAttribute.getWeight());

        return dto;
    }

    public List<ProductAttributeResponseDto> toDtoList(List<ProductAttribute> productAttributeList) {
        return productAttributeList.stream()
                .map(this::toDto)
                .toList();
    }

}
