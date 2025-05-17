package com.example.shopberry.domain.productpromotions.dto;

import com.example.shopberry.domain.productpromotions.ProductPromotion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductPromotionDtoMapper {

    public ProductPromotionResponseDto toDto(ProductPromotion productPromotion) {
        ProductPromotionResponseDto dto = new ProductPromotionResponseDto();

        dto.setProductId(productPromotion.getId().getProductId());
        dto.setPromotionId(productPromotion.getId().getPromotionId());

        return dto;
    }

    public List<ProductPromotionResponseDto> toDtoList(List<ProductPromotion> productPromotions) {
        return productPromotions.stream()
                .map(this::toDto)
                .toList();
    }

}
