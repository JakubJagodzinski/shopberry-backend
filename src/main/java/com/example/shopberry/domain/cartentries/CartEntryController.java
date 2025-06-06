package com.example.shopberry.domain.cartentries;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.cartentries.dto.CartEntryResponseDto;
import com.example.shopberry.domain.cartentries.dto.CreateCartEntryRequestDto;
import com.example.shopberry.domain.cartentries.dto.UpdateCartEntryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-entries")
@RequiredArgsConstructor
public class CartEntryController {

    private final CartEntryService cartEntryService;

    @GetMapping("/by-customer/{customerId}/by-product/{productId}")
    public ResponseEntity<CartEntryResponseDto> getCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        CartEntryResponseDto cartEntryResponseDto = cartEntryService.getCartEntryByCartEntryId(new CartEntryId(customerId, productId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartEntryResponseDto);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<CartEntryResponseDto>> getCartEntriesByCustomerId(@PathVariable Long customerId) {
        List<CartEntryResponseDto> cartEntryResponseDtoList = cartEntryService.getCartEntriesByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartEntryResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<CartEntryResponseDto> createCartEntry(@RequestBody CreateCartEntryRequestDto createCartEntryRequestDto) {
        CartEntryResponseDto createdCartEntryResponseDto = cartEntryService.createCartEntry(createCartEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/cart-entries/" + createdCartEntryResponseDto.getCustomerId() + "/" + createdCartEntryResponseDto.getProductId()))
                .body(createdCartEntryResponseDto);
    }

    @PutMapping("/by-customer/{customerId}/by-product/{productId}")
    public ResponseEntity<CartEntryResponseDto> updateCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody UpdateCartEntryRequestDto updateCartEntryRequestDto) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        CartEntryResponseDto updatedCartEntryResponseDto = cartEntryService.updateCartEntryByCartEntryId(cartEntryId, updateCartEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCartEntryResponseDto);
    }

    @DeleteMapping("/by-customer/{customerId}/by-product/{productId}")
    public ResponseEntity<MessageResponseDto> deleteCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        cartEntryService.deleteCartEntryByCartEntryId(cartEntryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Cart entry with ID " + cartEntryId + " deleted successfully."));
    }

}
