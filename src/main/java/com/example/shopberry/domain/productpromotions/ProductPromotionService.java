package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.common.constants.messages.PromotionMessages;
import com.example.shopberry.domain.productpromotions.dto.AssignPromotionToProductRequestDto;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionDtoMapper;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.domain.promotions.Promotion;
import com.example.shopberry.domain.promotions.PromotionRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPromotionService {

    private final ProductPromotionRepository productPromotionRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    private final ProductPromotionDtoMapper productPromotionDtoMapper;

    public List<ProductPromotionResponseDto> getAllProductPromotions() {
        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findAll());
    }

    @Transactional
    public List<ProductPromotionResponseDto> getProductAllPromotions(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findAllByProduct_ProductId(productId));
    }

    @Transactional
    public List<ProductPromotionResponseDto> getAllProductsWithPromotion(Long promotionId) throws EntityNotFoundException {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findAllByPromotion_PromotionId(promotionId));
    }

    @Transactional
    public ProductPromotionResponseDto assignPromotionToProduct(Long productId, AssignPromotionToProductRequestDto assignPromotionToProductRequestDto) throws EntityNotFoundException, EntityExistsException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        Promotion promotion = promotionRepository.findById(assignPromotionToProductRequestDto.getPromotionId()).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        ProductPromotionId productPromotionId = new ProductPromotionId(productId, assignPromotionToProductRequestDto.getPromotionId());

        if (productPromotionRepository.existsById(productPromotionId)) {
            throw new EntityExistsException(PromotionMessages.PROMOTION_ALREADY_ASSIGNED_TO_THIS_PRODUCT);
        }

        ProductPromotion productPromotion = new ProductPromotion();

        productPromotion.setId(productPromotionId);
        productPromotion.setProduct(product);
        productPromotion.setPromotion(promotion);

        return productPromotionDtoMapper.toDto(productPromotionRepository.save(productPromotion));
    }

    @Transactional
    public void unassignAllPromotionsFromProduct(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        productPromotionRepository.deleteAllByProduct_ProductId(productId);
    }

    @Transactional
    public void deleteProductPromotionsByPromotionId(Long promotionId) throws EntityNotFoundException {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException(PromotionMessages.PROMOTION_NOT_FOUND);
        }

        productPromotionRepository.deleteAllByPromotion_PromotionId(promotionId);
    }

}
