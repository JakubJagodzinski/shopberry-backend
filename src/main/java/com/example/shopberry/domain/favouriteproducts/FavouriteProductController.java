package com.example.shopberry.domain.favouriteproducts;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.favouriteproducts.dto.AddProductToFavouritesRequestDto;
import com.example.shopberry.domain.favouriteproducts.dto.FavouriteProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavouriteProductController {

    private final FavouriteProductService favouriteProductService;

    @GetMapping("/customers/{customerId}/favourites")
    public ResponseEntity<List<FavouriteProductResponseDto>> getCustomerAllFavourites(@PathVariable Long customerId) {
        List<FavouriteProductResponseDto> favouriteProductResponseDtoList = favouriteProductService.getCustomerAllFavourites(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favouriteProductResponseDtoList);
    }

    @PostMapping("/customers/{customerId}/favourites")
    public ResponseEntity<FavouriteProductResponseDto> addProductToCustomerFavourites(@PathVariable Long customerId, @RequestBody AddProductToFavouritesRequestDto addProductToFavouritesRequestDto) {
        FavouriteProductResponseDto createdFavouriteProductResponseDto = favouriteProductService.addProductToCustomerFavourites(customerId, addProductToFavouritesRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/favourites/" + createdFavouriteProductResponseDto.getProductId()))
                .body(createdFavouriteProductResponseDto);
    }

    @DeleteMapping("/customers/{customerId}/favourites/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromCustomerFavourites(@PathVariable Long customerId, @PathVariable Long productId) {
        favouriteProductService.removeProductFromCustomerFavourites(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from customer with id " + customerId + " favourites successfully"));
    }

}
