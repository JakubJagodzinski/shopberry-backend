package com.example.shopberry.domain.cartproducts.dto;

import com.example.shopberry.domain.cartproducts.CartProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartProductDtoMapper {

    public CartProductResponseDto toDto(CartProduct cartProduct) {
        CartProductResponseDto cartProductResponseDto = new CartProductResponseDto();

        cartProductResponseDto.setCustomerId(cartProduct.getCustomer().getUserId());
        cartProductResponseDto.setProductId(cartProduct.getProduct().getProductId());
        cartProductResponseDto.setQuantity(cartProduct.getQuantity());
        cartProductResponseDto.setAddedAt(cartProduct.getAddedAt());

        return cartProductResponseDto;
    }

    public List<CartProductResponseDto> toDtoList(List<CartProduct> cartProducts) {
        return cartProducts.stream()
                .map(this::toDto)
                .toList();
    }

}
