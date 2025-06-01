package com.example.shopberry.domain.promotions;

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

    private static final String PROMOTION_NOT_FOUND_MESSAGE = "Promotion not found";
    private static final String PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Promotion with that name already exists";
    private static final String PROMOTION_NAME_CANNOT_BE_NULL_MESSAGE = "Promotion name cannot be null";
    private static final String PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE = "Promotion name cannot be empty";
    private static final String PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE = "Promotion discount percent value cannot be negative";
    private static final String PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE = "Promotion discount percent value cannot be greater than 100";

    public List<PromotionResponseDto> getPromotions() {
        return promotionDtoMapper.toDtoList(promotionRepository.findAll());
    }

    @Transactional
    public PromotionResponseDto getPromotionById(Long id) throws EntityNotFoundException {
        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        return promotionDtoMapper.toDto(promotion);
    }

    @Transactional
    public PromotionResponseDto createPromotion(CreatePromotionRequestDto createPromotionRequestDto) throws IllegalArgumentException {
        if (promotionRepository.existsByPromotionName(createPromotionRequestDto.getPromotionName())) {
            throw new IllegalArgumentException(PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createPromotionRequestDto.getPromotionName() == null) {
            throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createPromotionRequestDto.getPromotionName().isEmpty()) {
            throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        if (createPromotionRequestDto.getDiscountPercentValue() < 0) {
            throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE);
        }

        if (createPromotionRequestDto.getDiscountPercentValue() > 100) {
            throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE);
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
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        if (updatePromotionRequestDto.getPromotionName() != null) {
            Promotion otherPromotion = promotionRepository.findByPromotionName(updatePromotionRequestDto.getPromotionName());

            if (otherPromotion != null && !otherPromotion.getPromotionId().equals(id)) {
                throw new IllegalArgumentException(PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updatePromotionRequestDto.getPromotionName().isEmpty()) {
                throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            promotion.setPromotionName(updatePromotionRequestDto.getPromotionName());
        }

        if (updatePromotionRequestDto.getDiscountPercentValue() != null) {
            if (updatePromotionRequestDto.getDiscountPercentValue() < 0) {
                throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE);
            }

            if (updatePromotionRequestDto.getDiscountPercentValue() > 100) {
                throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE);
            }

            promotion.setDiscountPercentValue(updatePromotionRequestDto.getDiscountPercentValue());
        }

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public void deletePromotionById(Long id) throws EntityNotFoundException {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        promotionRepository.deleteById(id);
    }

}
