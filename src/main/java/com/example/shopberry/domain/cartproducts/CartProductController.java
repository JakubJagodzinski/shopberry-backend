package com.example.shopberry.domain.cartproducts;

import com.example.shopberry.auth.access.CheckPermission;
import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.cartproducts.dto.AddProductToCartRequestDto;
import com.example.shopberry.domain.cartproducts.dto.CartProductResponseDto;
import com.example.shopberry.domain.cartproducts.dto.UpdateCartProductRequestDto;
import com.example.shopberry.user.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CartProductController {

    private final CartProductService cartProductService;

    @CheckPermission(Permission.CUSTOMER_CART_PRODUCT_READ)
    @GetMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<CartProductResponseDto> getCustomerCartProduct(@PathVariable UUID customerId, @PathVariable Long productId) {
        CartProductResponseDto cartProductResponseDto = cartProductService.getCustomerCartProduct(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartProductResponseDto);
    }

    @CheckPermission(Permission.CUSTOMER_CART_PRODUCT_READ_ALL)
    @GetMapping("/customers/{customerId}/cart")
    public ResponseEntity<List<CartProductResponseDto>> getCustomerAllCartProducts(@PathVariable UUID customerId) {
        List<CartProductResponseDto> cartProductResponseDtoList = cartProductService.getCustomerAllCartProducts(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cartProductResponseDtoList);
    }

    @CheckPermission(Permission.CART_PRODUCT_ADD)
    @PostMapping("/customers/{customerId}/cart")
    public ResponseEntity<CartProductResponseDto> addProductToCustomerCart(@PathVariable UUID customerId, @RequestBody AddProductToCartRequestDto addProductToCartRequestDto) {
        CartProductResponseDto createdCartProductResponseDto = cartProductService.addProductToCustomerCart(customerId, addProductToCartRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/cart/" + createdCartProductResponseDto.getProduct().getProductId()))
                .body(createdCartProductResponseDto);
    }

    @CheckPermission(Permission.CART_PRODUCT_UPDATE)
    @PatchMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<CartProductResponseDto> updateCustomerCartProduct(@PathVariable UUID customerId, @PathVariable Long productId, @RequestBody UpdateCartProductRequestDto updateCartProductRequestDto) {
        CartProductResponseDto updatedCartProductResponseDto = cartProductService.updateCustomerCartProduct(customerId, productId, updateCartProductRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedCartProductResponseDto);
    }

    @CheckPermission(Permission.CART_PRODUCT_REMOVE)
    @DeleteMapping("/customers/{customerId}/cart/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromCustomerCart(@PathVariable UUID customerId, @PathVariable Long productId) {
        cartProductService.removeProductFromCustomerCart(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from customer with id " + customerId + " cart successfully"));
    }

}
