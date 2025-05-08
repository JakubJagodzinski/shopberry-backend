package com.example.internet_shop.carts;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEntryDtoMapper {

    public CartEntryDto toDto(CartEntry cartEntry) {
        CartEntryDto cartEntryDto = new CartEntryDto();

        cartEntryDto.setCustomerId(cartEntry.getCustomer().getCustomerId());
        cartEntryDto.setProductId(cartEntry.getProduct().getProductId());
        cartEntryDto.setQuantity(cartEntry.getQuantity());
        cartEntryDto.setAddedAt(cartEntry.getAddedAt());

        return cartEntryDto;
    }

    public List<CartEntryDto> toDtoList(List<CartEntry> cartEntries) {
        return cartEntries.stream()
                .map(this::toDto)
                .toList();
    }

}
