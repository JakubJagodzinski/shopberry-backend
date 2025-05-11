package com.example.internet_shop.cartentries;

import com.example.internet_shop.cartentries.dto.CartEntryResponseDto;
import com.example.internet_shop.cartentries.dto.CreateCartEntryRequestDto;
import com.example.internet_shop.cartentries.dto.UpdateCartEntryRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart-entries")
public class CartEntryController {

    private final CartEntryService cartEntryService;

    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/{customerId}/{productId}")
    public ResponseEntity<CartEntryResponseDto> getCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartEntryService.getCartEntryByCartEntryId(new CartEntryId(customerId, productId)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartEntryResponseDto>> getCartEntriesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartEntryService.getCartEntriesByCustomerId(customerId));
    }

    @PostMapping("/")
    public ResponseEntity<CartEntryResponseDto> createCartEntry(@RequestBody CreateCartEntryRequestDto createCartEntryRequestDto) {
        CartEntryResponseDto createdCartEntry = cartEntryService.createCartEntry(createCartEntryRequestDto);

        return ResponseEntity.created(URI.create("/api/v1/cart-entries/" + createdCartEntry.getCustomerId() + "/" + createdCartEntry.getProductId())).body(createdCartEntry);
    }

    @PutMapping("/{customerId}/{productId}")
    public ResponseEntity<CartEntryResponseDto> updateCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody UpdateCartEntryRequestDto updateCartEntryRequestDto) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        return ResponseEntity.ok(cartEntryService.updateCartEntryByCartEntryId(cartEntryId, updateCartEntryRequestDto));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<String> deleteCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);
        cartEntryService.deleteCartEntryByCartEntryId(cartEntryId);

        return ResponseEntity.ok("Deleted cart entry with id: " + cartEntryId);
    }

}
