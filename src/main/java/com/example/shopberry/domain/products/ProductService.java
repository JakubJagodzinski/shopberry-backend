package com.example.shopberry.domain.products;

import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.domain.products.dto.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.products.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityExistsException;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<ProductResponseDto> getAllProducts() {
        return productDtoMapper.toDtoList(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDto getProductById(Long productId) throws EntityNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        return productDtoMapper.toDto(product);
    }

    @Transactional
    public List<ProductResponseDto> getCategoryAllProducts(Long categoryId) throws EntityNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        return productDtoMapper.toDtoList(productRepository.findAllByCategory_CategoryId(categoryId));
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) throws EntityExistsException {
        String productName = createProductRequestDto.getProductName().trim();

        if (productRepository.existsByProductName(productName)) {
            throw new EntityExistsException(ProductMessages.PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS);
        }

        Product product = new Product();

        product.setProductName(productName);
        product.setProductPrice(createProductRequestDto.getProductPrice());

        if (createProductRequestDto.getDiscountPercentValue() != null) {
            product.setDiscountPercentValue(createProductRequestDto.getDiscountPercentValue());
        }

        if (createProductRequestDto.getIsInStock() != null) {
            product.setIsInStock(createProductRequestDto.getIsInStock());
        }

        if (createProductRequestDto.getImage() != null) {
            product.setImage(createProductRequestDto.getImage());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long productId, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException, EntityExistsException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        if (updateProductRequestDto.getProductName() != null) {
            String productName = updateProductRequestDto.getProductName().trim();

            Product otherProduct = productRepository.findByProductName(productName).orElse(null);

            if (otherProduct != null && !otherProduct.getProductId().equals(productId)) {
                throw new EntityExistsException(ProductMessages.PRODUCT_WITH_THAT_NAME_ALREADY_EXISTS);
            }

            product.setProductName(productName);
        }

        if (updateProductRequestDto.getProductPrice() != null) {
            product.setProductPrice(updateProductRequestDto.getProductPrice());
        }

        if (updateProductRequestDto.getDiscountPercentValue() != null) {
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
    public void deleteProductById(Long productId) throws EntityNotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

}
