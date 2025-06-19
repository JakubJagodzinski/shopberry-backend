package com.example.shopberry.domain.promotions;

import com.example.shopberry.common.constants.messages.PromotionMessages;
import com.example.shopberry.domain.promotions.dto.CreatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.PromotionDtoMapper;
import com.example.shopberry.domain.promotions.dto.PromotionResponseDto;
import com.example.shopberry.domain.promotions.dto.UpdatePromotionRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    private final PromotionDtoMapper promotionDtoMapper;

    public List<PromotionResponseDto> getAllPromotions() {
        return promotionDtoMapper.toDtoList(promotionRepository.findAll());
    }

    @Transactional
    public PromotionResponseDto getPromotionById(Long id) throws EntityNotFoundException {
        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        return promotionDtoMapper.toDto(promotion);
    }

    @Transactional
    public PromotionResponseDto createPromotion(CreatePromotionRequestDto createPromotionRequestDto) throws IllegalArgumentException {
        if (promotionRepository.existsByPromotionName(createPromotionRequestDto.getPromotionName())) {
            throw new IllegalArgumentException(PromotionMessages.PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createPromotionRequestDto.getPromotionName() == null) {
            throw new IllegalArgumentException(PromotionMessages.PROMOTION_NAME_CANNOT_BE_NULL);
        }

        if (createPromotionRequestDto.getPromotionName().isEmpty()) {
            throw new IllegalArgumentException(PromotionMessages.PROMOTION_NAME_CANNOT_BE_EMPTY);
        }

        if (createPromotionRequestDto.getDiscountPercentValue() < 0) {
            throw new IllegalArgumentException(PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE);
        }

        if (createPromotionRequestDto.getDiscountPercentValue() > 100) {
            throw new IllegalArgumentException(PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100);
        }

        Promotion promotion = new Promotion();

        promotion.setPromotionName(createPromotionRequestDto.getPromotionName());
        promotion.setDiscountPercentValue(createPromotionRequestDto.getDiscountPercentValue());

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public PromotionResponseDto updatePromotionById(Long id, UpdatePromotionRequestDto updatePromotionRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        if (updatePromotionRequestDto.getPromotionName() != null) {
            Promotion otherPromotion = promotionRepository.findByPromotionName(updatePromotionRequestDto.getPromotionName()).orElse(null);

            if (otherPromotion != null && !otherPromotion.getPromotionId().equals(id)) {
                throw new IllegalArgumentException(PromotionMessages.PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            if (updatePromotionRequestDto.getPromotionName().isEmpty()) {
                throw new IllegalArgumentException(PromotionMessages.PROMOTION_NAME_CANNOT_BE_EMPTY);
            }

            promotion.setPromotionName(updatePromotionRequestDto.getPromotionName());
        }

        if (updatePromotionRequestDto.getDiscountPercentValue() != null) {
            if (updatePromotionRequestDto.getDiscountPercentValue() < 0) {
                throw new IllegalArgumentException(PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE);
            }

            if (updatePromotionRequestDto.getDiscountPercentValue() > 100) {
                throw new IllegalArgumentException(PromotionMessages.PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100);
            }

            promotion.setDiscountPercentValue(updatePromotionRequestDto.getDiscountPercentValue());
        }

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public void deletePromotionById(Long id) throws EntityNotFoundException {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        promotionRepository.deleteById(id);
    }

}
