package com.example.shopberry.domain.promotions;

import com.example.shopberry.common.constants.messages.PromotionMessages;
import com.example.shopberry.domain.promotions.dto.request.CreatePromotionRequestDto;
import com.example.shopberry.domain.promotions.dto.PromotionDtoMapper;
import com.example.shopberry.domain.promotions.dto.response.PromotionResponseDto;
import com.example.shopberry.domain.promotions.dto.request.UpdatePromotionRequestDto;
import jakarta.persistence.EntityExistsException;
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
    public PromotionResponseDto getPromotionById(Long promotionId) throws EntityNotFoundException {
        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        return promotionDtoMapper.toDto(promotion);
    }

    @Transactional
    public PromotionResponseDto createPromotion(CreatePromotionRequestDto createPromotionRequestDto) throws EntityExistsException {
        String promotionName = createPromotionRequestDto.getPromotionName().trim();

        if (promotionRepository.existsByPromotionName(promotionName)) {
            throw new EntityExistsException(PromotionMessages.PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        Promotion promotion = new Promotion();

        promotion.setPromotionName(promotionName);
        promotion.setDiscountPercentValue(createPromotionRequestDto.getDiscountPercentValue());

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public PromotionResponseDto updatePromotionById(Long promotionId, UpdatePromotionRequestDto updatePromotionRequestDto) throws EntityNotFoundException, EntityExistsException {
        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        if (updatePromotionRequestDto.getPromotionName() != null) {
            String promotionName = updatePromotionRequestDto.getPromotionName().trim();

            Promotion otherPromotion = promotionRepository.findByPromotionName(promotionName).orElse(null);

            if (otherPromotion != null && !otherPromotion.getPromotionId().equals(promotionId)) {
                throw new EntityExistsException(PromotionMessages.PROMOTION_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            promotion.setPromotionName(promotionName);
        }

        if (updatePromotionRequestDto.getDiscountPercentValue() != null) {
            promotion.setDiscountPercentValue(updatePromotionRequestDto.getDiscountPercentValue());
        }

        return promotionDtoMapper.toDto(promotionRepository.save(promotion));
    }

    @Transactional
    public void deletePromotionById(Long promotionId) throws EntityNotFoundException {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        promotionRepository.deleteById(promotionId);
    }

}
