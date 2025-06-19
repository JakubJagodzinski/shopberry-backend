package com.example.shopberry.domain.favouriteentries;

import com.example.shopberry.common.MessageResponseDto;
import com.example.shopberry.domain.favouriteentries.dto.AddProductToCustomerFavouritesRequestDto;
import com.example.shopberry.domain.favouriteentries.dto.FavouriteEntryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FavouriteEntryController {

    private final FavouriteEntryService favouriteEntryService;

    @GetMapping("/customers/{customerId}/favourites")
    public ResponseEntity<List<FavouriteEntryResponseDto>> getCustomerAllFavourites(@PathVariable Long customerId) {
        List<FavouriteEntryResponseDto> favouriteEntryResponseDtoList = favouriteEntryService.getCustomerAllFavourites(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favouriteEntryResponseDtoList);
    }

    @PostMapping("/customers/{customerId}/favourites")
    public ResponseEntity<FavouriteEntryResponseDto> addProductToCustomerFavourites(@PathVariable Long customerId, @RequestBody AddProductToCustomerFavouritesRequestDto addProductToCustomerFavouritesRequestDto) {
        FavouriteEntryResponseDto createdFavouriteEntryResponseDto = favouriteEntryService.addProductToCustomerFavourites(customerId, addProductToCustomerFavouritesRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/customers/" + customerId + "/favourites/" + createdFavouriteEntryResponseDto.getProductId()))
                .body(createdFavouriteEntryResponseDto);
    }

    @DeleteMapping("/customers/{customerId}/favourites/{productId}")
    public ResponseEntity<MessageResponseDto> removeProductFromCustomerFavourites(@PathVariable Long customerId, @PathVariable Long productId) {
        favouriteEntryService.removeProductFromCustomerFavourites(customerId, productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponseDto("Product with id " + productId + " removed from customer with id " + customerId + " favourites successfully"));
    }

}
