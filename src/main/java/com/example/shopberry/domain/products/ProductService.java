package com.example.shopberry.domain.products;

import com.example.shopberry.common.constants.messages.CategoryMessages;
import com.example.shopberry.common.constants.messages.ProducerMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.attributes.dto.AttributeDtoMapper;
import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.domain.producers.Producer;
import com.example.shopberry.domain.producers.ProducerRepository;
import com.example.shopberry.domain.productattributes.ProductAttribute;
import com.example.shopberry.domain.productattributes.ProductAttributeRepository;
import com.example.shopberry.domain.productattributes.dto.AttributeValueDto;
import com.example.shopberry.domain.productattributes.dto.ProductWithAttributesResponseDto;
import com.example.shopberry.domain.products.dto.CreateProductRequestDto;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import com.example.shopberry.domain.products.dto.ProductResponseDto;
import com.example.shopberry.domain.products.dto.UpdateProductRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final ProductAttributeRepository productAttributeRepository;

    private final ProductDtoMapper productDtoMapper;
    private final AttributeDtoMapper attributeDtoMapper;

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
    public List<ProductWithAttributesResponseDto> getCategoryAllProducts(Long categoryId) throws EntityNotFoundException {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        List<Product> productList;

        if (categoryRepository.existsByParentCategory_CategoryId(categoryId)) {
            productList = new ArrayList<>();
            List<Category> childCategories = categoryRepository.findAllByParentCategory_CategoryId(categoryId);

            for (Category childCategory : childCategories) {
                productList.addAll(productRepository.findAllByCategory_CategoryId(childCategory.getCategoryId()));
            }
        } else {
            productList = productRepository.findAllByCategory_CategoryId(categoryId);
        }

        List<ProductWithAttributesResponseDto> productWithAttributesResponseDtoList = new ArrayList<>();

        for (Product product : productList) {
            ProductWithAttributesResponseDto productWithAttributesResponseDto = new ProductWithAttributesResponseDto();

            productWithAttributesResponseDto.setProduct(productDtoMapper.toDto(product));

            List<ProductAttribute> productAttributeDtoList = productAttributeRepository.findAllByProduct_ProductIdOrderByWeightDesc(product.getProductId());

            List<AttributeValueDto> attributeValueDtoList = new ArrayList<>();

            for (ProductAttribute productAttribute : productAttributeDtoList) {
                AttributeValueDto attributeValueDto = new AttributeValueDto();

                attributeValueDto.setAttribute(attributeDtoMapper.toDto(productAttribute.getAttribute()));
                attributeValueDto.setValue(productAttribute.getValue());
                attributeValueDto.setWeight(productAttribute.getWeight());

                attributeValueDtoList.add(attributeValueDto);
            }

            productWithAttributesResponseDto.setAttributes(attributeValueDtoList);

            productWithAttributesResponseDtoList.add(productWithAttributesResponseDto);
        }

        return productWithAttributesResponseDtoList;
    }

    @Transactional
    public ProductResponseDto createProduct(CreateProductRequestDto createProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = new Product();

        product.setProductName(createProductRequestDto.getProductName().trim());
        product.setProductPrice(createProductRequestDto.getProductPrice());

        if (createProductRequestDto.getDiscountPercentValue() != null) {
            product.setDiscountPercentValue(createProductRequestDto.getDiscountPercentValue());
        }

        Producer producer = producerRepository.findById(createProductRequestDto.getProducerId()).orElse(null);

        if (producer == null) {
            throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
        }

        product.setProducer(producer);

        Category category = categoryRepository.findById(createProductRequestDto.getCategoryId()).orElse(null);

        if (category == null) {
            throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
        }

        if (categoryRepository.existsByParentCategory_CategoryId(category.getCategoryId())) {
            throw new IllegalArgumentException(CategoryMessages.CATEGORY_IS_PARENT_CATEGORY);
        }

        product.setCategory(category);

        if (createProductRequestDto.getIsInStock() != null) {
            product.setIsInStock(createProductRequestDto.getIsInStock());
        }

        if (createProductRequestDto.getImage() != null) {
            product.setImage(createProductRequestDto.getImage());
        }

        return productDtoMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDto updateProductById(Long productId, UpdateProductRequestDto updateProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        if (updateProductRequestDto.getProductName() != null) {
            product.setProductName(updateProductRequestDto.getProductName().trim());
        }

        if (updateProductRequestDto.getProductPrice() != null) {
            product.setProductPrice(updateProductRequestDto.getProductPrice());
        }

        if (updateProductRequestDto.getDiscountPercentValue() != null) {
            product.setDiscountPercentValue(updateProductRequestDto.getDiscountPercentValue());
        }

        if (updateProductRequestDto.getProducerId() != null) {
            Producer producer = producerRepository.findById(updateProductRequestDto.getProducerId()).orElse(null);

            if (producer == null) {
                throw new EntityNotFoundException(ProducerMessages.PRODUCER_NOT_FOUND);
            }

            product.setProducer(producer);
        }

        if (updateProductRequestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateProductRequestDto.getCategoryId()).orElse(null);

            if (category == null) {
                throw new EntityNotFoundException(CategoryMessages.CATEGORY_NOT_FOUND);
            }

            if (categoryRepository.existsByParentCategory_CategoryId(category.getCategoryId())) {
                throw new IllegalArgumentException(CategoryMessages.CATEGORY_IS_PARENT_CATEGORY);
            }

            product.setCategory(category);
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
