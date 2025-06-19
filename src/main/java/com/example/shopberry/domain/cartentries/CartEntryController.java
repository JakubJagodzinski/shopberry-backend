package com.example.shopberry.domain.cartentries;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.cartentries.dto.AddProductToCustomerCartRequestDto;
import com.example.shopberry.domain.cartentries.dto.CartEntryResponseDto;
import com.example.shopberry.domain.cartentries.dto.UpdateCartEntryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartEntryController {

    private final CartEntryService cartEntryService;

    @GetMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<CartEntryResponseDto> getCustomerCartProduct(@PathVariable Long customerId, @PathVariable Long productId) {
        CartEntryResponseDto cartEntryResponseDto = cartEntryService.getCustomerCartProduct(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartEntryResponseDto);
    }

    @GetMapping("/customers/{customerId}/cart")
    public ResponseEntity<List<CartEntryResponseDto>> getCustomerAllCartProducts(@PathVariable Long customerId) {
        List<CartEntryResponseDto> cartEntryResponseDtoList = cartEntryService.getCustomerAllCartProducts(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartEntryResponseDtoList);
    }

    @PostMapping("/customers/{customerId}/cart")
    public ResponseEntity<CartEntryResponseDto> addProductToCustomerCart(@PathVariable Long customerId, @RequestBody AddProductToCustomerCartRequestDto addProductToCustomerCartRequestDto) {
        CartEntryResponseDto createdCartEntryResponseDto = cartEntryService.addProductToCustomerCart(customerId, addProductToCustomerCartRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/cart/" + createdCartEntryResponseDto.getProductId()))
                .body(createdCartEntryResponseDto);
    }

    @PatchMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<CartEntryResponseDto> updateCustomerCartProduct(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody UpdateCartEntryRequestDto updateCartEntryRequestDto) {
        CartEntryResponseDto updatedCartEntryResponseDto = cartEntryService.updateCustomerCartProduct(customerId, productId, updateCartEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCartEntryResponseDto);
    }

    @DeleteMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromCustomerCart(@PathVariable Long customerId, @PathVariable Long productId) {
        cartEntryService.removeProductFromCustomerCart(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from customer with id " + customerId + " cart successfully"));
    }

}
