package com.example.shopberry.domain.cartproducts.dto;

import com.example.shopberry.domain.cartproducts.CartProduct;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartProductDtoMapper {

    private final ProductDtoMapper productDtoMapper;

    public CartProductResponseDto toDto(CartProduct cartProduct) {
        if (cartProduct == null) {
            return null;
        }

        CartProductResponseDto cartProductResponseDto = new CartProductResponseDto();

        if (cartProduct.getCustomer() != null) {
            cartProductResponseDto.setCustomerId(cartProduct.getCustomer().getUserId());
        } else {
            cartProductResponseDto.setCustomerId(null);
        }
        cartProductResponseDto.setProduct(productDtoMapper.toDto(cartProduct.getProduct()));
        cartProductResponseDto.setProductQuantity(cartProduct.getProductQuantity());
        cartProductResponseDto.setAddedAt(cartProduct.getAddedAt());

        return cartProductResponseDto;
    }

    public List<CartProductResponseDto> toDtoList(List<CartProduct> cartProductList) {
        return cartProductList.stream()
                .map(this::toDto)
                .toList();
    }

}
