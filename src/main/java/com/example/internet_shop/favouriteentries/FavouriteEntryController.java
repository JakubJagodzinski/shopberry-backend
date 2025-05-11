package com.example.internet_shop.favouriteentries;

import com.example.internet_shop.favouriteentries.dto.CreateFavouriteEntryRequestDto;
import com.example.internet_shop.favouriteentries.dto.FavouriteEntryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favourite-entries")
public class FavouriteEntryController {

    private final FavouriteEntryService favouriteEntryService;

    public FavouriteEntryController(FavouriteEntryService favouriteEntryService) {
        this.favouriteEntryService = favouriteEntryService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<FavouriteEntryResponseDto>> getFavouriteEntriesByCustomerId(@PathVariable Long customerId) {
        List<FavouriteEntryResponseDto> favouriteEntryResponseDtoList = favouriteEntryService.getFavouriteEntriesByCustomerId(customerId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(favouriteEntryResponseDtoList);
    }

    @PostMapping("/")
    public ResponseEntity<FavouriteEntryResponseDto> createFavouriteEntry(@RequestBody CreateFavouriteEntryRequestDto createFavouriteEntryRequestDto) {
        FavouriteEntryResponseDto createdFavouriteEntryResponseDto = favouriteEntryService.createFavouriteEntry(createFavouriteEntryRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(URI.create("/api/v1/favourite-entries/" + createdFavouriteEntryResponseDto.getCustomerId() + "/" + createdFavouriteEntryResponseDto.getProductId()))
                .body(createdFavouriteEntryResponseDto);
    }

    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<String> deleteFavouriteEntryByFavouriteEntryId(@PathVariable Long customerId, @PathVariable Long productId) {
        FavouriteEntryId favouriteEntryId = new FavouriteEntryId(productId, customerId);

        favouriteEntryService.deleteFavouriteEntryByFavouriteEntryId(favouriteEntryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Favourite entry with id " + favouriteEntryId + " deleted successfully");
    }

}
