package com.example.shopberry.domain.products;

import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.products.dto.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.products.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductDtoMapper productDtoMapper;

    public List<ProductResponseDto> getAllProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDto getProductById(Long id) throws EntityNotFoundException {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return productDtoMapper.toDto(product);
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) throws IllegalArgumentException {
        if (productRepository.existsByProductName(createProductRequestDto.getProductName())) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        if (createProductRequestDto.getProductName() == null) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_NAME_CANNOT_BE_NULL);
        }

        if (createProductRequestDto.getProductName().isEmpty()) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_NAME_CANNOT_BE_EMPTY);
        }

        Product product = new Product();

        product.setProductName(createProductRequestDto.getProductName());
        product.setProductPrice(createProductRequestDto.getProductPrice());
        product.setIsInStock(createProductRequestDto.getIsInStock());
        product.setImage(createProductRequestDto.getImage());

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long id, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        if (updateProductRequestDto.getProductName() != null) {
            Product otherProduct = productRepository.findByProductName(updateProductRequestDto.getProductName()).orElse(null);

            if (otherProduct != null && !otherProduct.getProductId().equals(id)) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            product.setProductName(updateProductRequestDto.getProductName());
        }

        if (updateProductRequestDto.getProductPrice() != null) {
            if (updateProductRequestDto.getProductPrice() < 0) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_PRICE_CANNOT_BE_NEGATIVE);
            }

            product.setProductPrice(updateProductRequestDto.getProductPrice());
        }

        if (updateProductRequestDto.getDiscountPercentValue() != null) {
            if (updateProductRequestDto.getDiscountPercentValue() < 0) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_DISCOUNT_PERCENT_VALUE_CANNOT_BE_NEGATIVE);
            }
            if (updateProductRequestDto.getDiscountPercentValue() > 100) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_DISCOUNT_PERCENT_VALUE_CANNOT_BE_GREATER_THAN_100);
            }

            product.setDiscountPercentValue(updateProductRequestDto.getDiscountPercentValue());
        }

        if (updateProductRequestDto.getIsInStock() != null) {
            product.setIsInStock(updateProductRequestDto.getIsInStock());
        }

        if (updateProductRequestDto.getImage() != null) {
            product.setImage(updateProductRequestDto.getImage());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(Long id) throws EntityNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
    }

}
