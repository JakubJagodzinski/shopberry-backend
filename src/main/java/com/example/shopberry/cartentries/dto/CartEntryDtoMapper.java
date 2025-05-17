package com.example.shopberry.cartentries.dto;

import com.example.shopberry.cartentries.CartEntry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartEntryDtoMapper {

    public CartEntryResponseDto toDto(CartEntry cartEntry) {
        CartEntryResponseDto cartEntryResponseDto = new CartEntryResponseDto();

        cartEntryResponseDto.setCustomerId(cartEntry.getCustomer().getCustomerId());
        cartEntryResponseDto.setProductId(cartEntry.getProduct().getProductId());
        cartEntryResponseDto.setQuantity(cartEntry.getQuantity());
        cartEntryResponseDto.setAddedAt(cartEntry.getAddedAt());

        return cartEntryResponseDto;
    }

    public List<CartEntryResponseDto> toDtoList(List<CartEntry> cartEntries) {
        return cartEntries.stream()
                .map(this::toDto)
                .toList();
    }

}
