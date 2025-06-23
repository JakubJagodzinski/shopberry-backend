package com.example.shopberry.domain.products.dto;

import com.example.shopberry.domain.producers.dto.ProducerDtoMapper;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.dto.response.ProductResponseDto;
import com.example.shopberry.utils.ImageBase64Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDtoMapper {

    private final ProducerDtoMapper producerDtoMapper;

    public ProductResponseDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDto dto = new ProductResponseDto();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setProductPrice(product.getProductPrice());
        dto.setDiscountPercentValue(product.getDiscountPercentValue());
        dto.setRatingValue(product.getRatingValue());
        dto.setRatingsCount(product.getRatingsCount());
        dto.setProducer(producerDtoMapper.toDto(product.getProducer()));
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getCategoryId());
        } else {
            dto.setCategoryId(null);
        }
        dto.setIsInStock(product.getIsInStock());
        if (product.getImage() != null) {
            String base64 = ImageBase64Utils.encode(product.getImage());
            dto.setImage(base64);
        }

        return dto;
    }

    public List<ProductResponseDto> toDtoList(List<Product> productList) {
        return productList.stream()
                .map(this::toDto)
                .toList();
    }

}
