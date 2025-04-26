package com.example.internet_shop.promotions;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionDtoMapper {

    public PromotionDto toDto(Promotion promotion) {
        PromotionDto PromotionDto = new PromotionDto();

        PromotionDto.setPromotionId(promotion.getPromotionId());
        PromotionDto.setPromotionName(promotion.getPromotionName());
        PromotionDto.setDiscountPercentValue(promotion.getDiscountPercentValue());

        return PromotionDto;
    }

    public List<PromotionDto> toDtoList(List<Promotion> promotions) {
        return promotions.stream()
                .map(this::toDto)
                .toList();
    }

}
