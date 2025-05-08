package com.example.internet_shop.cartentries;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cart_entries")
public class CartEntryController {

    private final CartEntryService cartEntryService;

    public CartEntryController(CartEntryService cartEntryService) {
        this.cartEntryService = cartEntryService;
    }

    @GetMapping("/{customerId}/{productId}")
    public ResponseEntity<CartEntryDto> getCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartEntryService.getCartEntryByCartEntryId(new CartEntryId(customerId, productId)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartEntryDto>> getCartEntriesByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartEntryService.getCartEntriesByCustomerId(customerId));
    }

    @PostMapping("/")
    public ResponseEntity<CartEntryDto> createCartEntry(@RequestBody CreateCartEntryDto createCartEntryDto) {
        CartEntryDto createdCartEntry = cartEntryService.createCartEntry(createCartEntryDto);

        return ResponseEntity.created(URI.create("/api/cart_entries/" + createdCartEntry.getCustomerId() + "/" + createdCartEntry.getProductId())).body(createdCartEntry);
    }

    @PutMapping("/{customerId}/{productId}")
    public ResponseEntity<CartEntryDto> updateCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody UpdateCartEntryDto updateCartEntryDto) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        return ResponseEntity.ok(cartEntryService.updateCartEntryByCartEntryId(cartEntryId, updateCartEntryDto));
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<String> deleteCartEntryByCartEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);
        cartEntryService.deleteCartEntryByCartEntryId(cartEntryId);

        return ResponseEntity.ok("Deleted cart entry with id: " + cartEntryId);
    }

}
