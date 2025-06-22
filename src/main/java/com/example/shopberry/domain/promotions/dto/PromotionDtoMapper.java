package com.example.shopberry.domain.promotions.dto;

import com.example.shopberry.domain.promotions.Promotion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionDtoMapper {

    public PromotionResponseDto toDto(Promotion promotion) {
        if (promotion == null) {
            return null;
        }

        PromotionResponseDto PromotionResponseDto = new PromotionResponseDto();

        PromotionResponseDto.setPromotionId(promotion.getPromotionId());
        PromotionResponseDto.setPromotionName(promotion.getPromotionName());
        PromotionResponseDto.setDiscountPercentValue(promotion.getDiscountPercentValue());

        return PromotionResponseDto;
    }

    public List<PromotionResponseDto> toDtoList(List<Promotion> promotionList) {
        return promotionList.stream()
                .map(this::toDto)
                .toList();
    }

}
