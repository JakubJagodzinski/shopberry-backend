package com.example.shopberry.domain.productpromotions;

import com.example.shopberry.domain.productpromotions.dto.AssignPromotionToProductRequestDto;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionDtoMapper;
import com.example.shopberry.domain.productpromotions.dto.ProductPromotionResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.domain.promotions.Promotion;
import com.example.shopberry.domain.promotions.PromotionRepository;
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

    private static final String PROMOTION_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE = "Promotion already assigned to this product";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PROMOTION_NOT_FOUND_MESSAGE = "Promotion not found";

    public List<ProductPromotionResponseDto> getAllProductPromotions() {
        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findAll());
    }

    @Transactional
    public List<ProductPromotionResponseDto> getProductAllPromotions(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findById_ProductId(productId));
    }

    @Transactional
    public List<ProductPromotionResponseDto> getAllProductsWithPromotion(Long promotionId) throws EntityNotFoundException {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        return productPromotionDtoMapper.toDtoList(productPromotionRepository.findById_PromotionId(promotionId));
    }

    @Transactional
    public ProductPromotionResponseDto assignPromotionToProduct(Long productId, AssignPromotionToProductRequestDto assignPromotionToProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        Promotion promotion = promotionRepository.findById(assignPromotionToProductRequestDto.getPromotionId()).orElse(null);

        if (promotion == null) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        ProductPromotionId productPromotionId = new ProductPromotionId(productId, assignPromotionToProductRequestDto.getPromotionId());

        if (productPromotionRepository.existsById(productPromotionId)) {
            throw new IllegalArgumentException(PROMOTION_ALREADY_ASSIGNED_TO_THIS_PRODUCT_MESSAGE);
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
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        productPromotionRepository.deleteById_ProductId(productId);
    }

    @Transactional
    public void deleteProductPromotionsByPromotionId(Long promotionId) throws EntityNotFoundException {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException(PROMOTION_NOT_FOUND_MESSAGE);
        }

        productPromotionRepository.deleteById_PromotionId(promotionId);
    }

}
