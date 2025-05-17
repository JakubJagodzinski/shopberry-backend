package com.example.shopberry.promotions.dto;

import com.example.shopberry.promotions.Promotion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionDtoMapper {

    public PromotionResponseDto toDto(Promotion promotion) {
        PromotionResponseDto PromotionResponseDto = new PromotionResponseDto();

        PromotionResponseDto.setPromotionId(promotion.getPromotionId());
        PromotionResponseDto.setPromotionName(promotion.getPromotionName());
        PromotionResponseDto.setDiscountPercentValue(promotion.getDiscountPercentValue());

        return PromotionResponseDto;
    }

    public List<PromotionResponseDto> toDtoList(List<Promotion> promotions) {
        return promotions.stream()
                .map(this::toDto)
                .toList();
    }

}
