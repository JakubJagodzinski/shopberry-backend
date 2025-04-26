package com.example.internet_shop.promotions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    private final PromotionDtoMapper promotionDtoMapper;

    private final String PROMOTION_NOT_FOUND_MESSAGE = "Promotion not found";
    private final String PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE = "Promotion with that name already exists";
    private final String PROMOTION_NAME_CANNOT_BE_NULL_MESSAGE = "Promotion name cannot be null";
    private final String PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE = "Promotion name cannot be empty";
    private final String PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE = "Promotion discount percent value cannot be negative";
    private final String PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE = "Promotion discount percent value cannot be greater than 100";

    public PromotionService(PromotionRepository promotionRepository, PromotionDtoMapper promotionDtoMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionDtoMapper = promotionDtoMapper;
    }

    public List<PromotionDto> getPromotions() {
        return promotionDtoMapper.toDtoList(promotionRepository.findAll());
    }

    @Transactional
    public PromotionDto getPromotionById(Long id) throws EntityNotFoundException {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        return promotionDtoMapper.toDto(promotionRepository.getReferenceById(id));
    }

    @Transactional
    public PromotionDto createPromotion(CreatePromotionDto createPromotionDto) throws IllegalArgumentException {
        if (promotionRepository.existsByPromotionName(createPromotionDto.getPromotionName())) {
            throw new IllegalArgumentException(PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
        }

        if (createPromotionDto.getPromotionName() == null) {
            throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_NULL_MESSAGE);
        }

        if (createPromotionDto.getPromotionName().isEmpty()) {
            throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE);
        }

        if (createPromotionDto.getDiscountPercentValue() < 0) {
            throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE);
        }

        if (createPromotionDto.getDiscountPercentValue() > 100) {
            throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE);
        }

        Promotion promotion = new Promotion();

        promotion.setPromotionName(createPromotionDto.getPromotionName());
        promotion.setDiscountPercentValue(createPromotionDto.getDiscountPercentValue());

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public PromotionDto updatePromotionById(Long id, UpdatePromotionDto updatePromotionDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        Promotion promotion = promotionRepository.getReferenceById(id);

        if (updatePromotionDto.getPromotionName() != null) {
            Promotion otherPromotion = promotionRepository.findByPromotionName(updatePromotionDto.getPromotionName());

            if (otherPromotion != null && !otherPromotion.getPromotionId().equals(id)) {
                throw new IllegalArgumentException(PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS_MESSAGE);
            }

            if (updatePromotionDto.getPromotionName().isEmpty()) {
                throw new IllegalArgumentException(PROMOTION_NAME_CANNOT_BE_EMPTY_MESSAGE);
            }

            promotion.setPromotionName(updatePromotionDto.getPromotionName());
        }

        if (updatePromotionDto.getDiscountPercentValue() != null) {
            if (updatePromotionDto.getDiscountPercentValue() < 0) {
                throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE_MESSAGE);
            }

            if (updatePromotionDto.getDiscountPercentValue() > 100) {
                throw new IllegalArgumentException(PROMOTION_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100_MESSAGE);
            }

            promotion.setDiscountPercentValue(updatePromotionDto.getDiscountPercentValue());
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
