package com.example.shopberry.domain.productpromotions.dto;

import com.example.shopberry.domain.productpromotions.ProductPromotion;
import com.example.shopberry.domain.productpromotions.dto.response.ProductPromotionResponseDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import com.example.shopberry.domain.promotions.dto.PromotionDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductPromotionDtoMapper {

    private final ProductDtoMapper productDtoMapper;
    private final PromotionDtoMapper promotionDtoMapper;

    public ProductPromotionResponseDto toDto(ProductPromotion productPromotion) {
        if (productPromotion == null) {
            return null;
        }

        ProductPromotionResponseDto dto = new ProductPromotionResponseDto();

        dto.setProduct(productDtoMapper.toDto(productPromotion.getProduct()));
        dto.setPromotion(promotionDtoMapper.toDto(productPromotion.getPromotion()));

        return dto;
    }

    public List<ProductPromotionResponseDto> toDtoList(List<ProductPromotion> productPromotionList) {
        return productPromotionList.stream()
                .map(this::toDto)
                .toList();
    }

}
